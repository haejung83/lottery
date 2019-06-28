package com.github.haejung83.data

import com.github.haejung83.data.local.Lottery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface LotteryDataSource {

    fun getLotteries(): Flowable<List<Lottery>>

    fun getLotteryByDrawNumber(drawNumber: Int): Flowable<Lottery>

    fun insert(vararg lotteries: Lottery): Completable

    fun update(lottery: Lottery): Completable

    fun deleteByDrawNumber(drawNumber: Int): Completable

    fun deleteAll(): Completable

    fun count(): Single<Int>

    fun refresh(): Completable

}