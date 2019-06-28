package com.github.haejung83.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.haejung83.R
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.data.provideLotteryRepository
import com.github.haejung83.extend.moveToActivity
import com.github.haejung83.presentation.main.MainActivity
import com.github.haejung83.presentation.welcome.WelcomeActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    private lateinit var repository: LotteryRepository
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        repository = provideLotteryRepository(applicationContext)
        checkLotteryAndRefreshIfNeeds()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    private fun checkLotteryAndRefreshIfNeeds() {
        repository.count()
            .subscribeOn(Schedulers.io())
            .subscribe({ count ->
                Timber.i("Count: $count")
                if (count != LotteryRepository.LOTTERY_DRAW_NUMBER_CACHE_LIMIT) {
                    refreshLotteries()
                } else {
                    moveToActivity(MainActivity::class.java)
                }
            }, {
                Timber.e(it)
                finish()
            })
            .addTo(disposable)
    }

    private fun refreshLotteries() {
        repository.refresh()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Timber.i("Refresh: Done")
                moveToActivity(WelcomeActivity::class.java)
            }, {
                Timber.e(it)
                finish()
            })
            .addTo(disposable)
    }

}
