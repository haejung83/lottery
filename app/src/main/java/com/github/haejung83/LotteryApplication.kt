package com.github.haejung83

import android.app.Application
import timber.log.Timber

class LotteryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    // Initialize the Timber lib for logging
    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}