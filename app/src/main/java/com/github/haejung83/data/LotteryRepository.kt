package com.github.haejung83.data

import androidx.paging.DataSource
import com.github.haejung83.data.local.Lottery
import com.github.haejung83.data.local.LotteryDao
import com.github.haejung83.data.remote.LotteryAPI
import com.github.haejung83.data.remote.LotteryDTO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

class LotteryRepository(
    private val lotteryDao: LotteryDao
) : LotteryDataSource {

    private val lotteryAPI: LotteryAPI =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(LotteryAPI.BASE_URL)
            .build()
            .create(LotteryAPI::class.java)

    override fun getLotteries(): Flowable<List<Lottery>> =
        lotteryDao.findAll()

    override fun getLotteriesForPaging(): DataSource.Factory<Int, Lottery> =
        lotteryDao.findAllForPaging()

    override fun getLotteryByDrawNumber(drawNumber: Int): Flowable<Lottery> =
        lotteryDao.findByDrawNumber(drawNumber)

    override fun insert(vararg lotteries: Lottery) =
        lotteryDao.insert(*lotteries)

    override fun update(lottery: Lottery) =
        lotteryDao.update(lottery)

    override fun deleteByDrawNumber(drawNumber: Int) =
        lotteryDao.deleteByDrawNumber(drawNumber)

    override fun deleteAll() =
        lotteryDao.deleteAll()

    override fun count(): Single<Int> =
        lotteryDao.count()

    override fun refresh(): Completable =
        lotteryDao.deleteAll()
            .doOnComplete { Timber.i("deleteAll: complete") }
            .andThen(getLotteriesFromRemoteAndInsertToLocal())

    private fun getLotteriesFromRemoteAndInsertToLocal() =
        Completable.create { emitter ->
            val lotteryFetchCount = AtomicInteger(0)
            for (drawNumber in 1..LOTTERY_DRAW_NUMBER_CACHE_LIMIT)
                lotteryAPI.getLottery(drawNumber = drawNumber)
                    .enqueue(object : Callback<LotteryDTO> {
                        override fun onFailure(call: Call<LotteryDTO>, t: Throwable) {
                            Timber.i("onFailure: $t")
                            emitter.onError(t)
                        }

                        override fun onResponse(call: Call<LotteryDTO>, response: Response<LotteryDTO>) {
                            Timber.i("onResponse: ${lotteryFetchCount.get()}")
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    insert(getLotteryFromDto(it))
                                        .subscribeOn(Schedulers.io())
                                        .subscribe {
                                            if (lotteryFetchCount.incrementAndGet() >= LOTTERY_DRAW_NUMBER_CACHE_LIMIT) {
                                                emitter.onComplete()
                                            }
                                        }
                                }
                            } else {
                                emitter.onError(Throwable("Failed to get data from lotto API"))
                            }
                        }
                    })
        }

    companion object {
        const val LOTTERY_DRAW_NUMBER_CACHE_LIMIT = 50
//        const val LOTTERY_DRAW_NUMBER_CACHE_LIMIT = 865
    }

}