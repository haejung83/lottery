package com.github.haejung83.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.data.provideLotteryRepository
import com.github.haejung83.presentation.history.HistoryViewModel
import com.github.haejung83.presentation.main.MainViewModel
import com.github.haejung83.presentation.retrieve.RetrieveViewModel
import com.github.haejung83.presentation.splash.SplashViewModel
import com.github.haejung83.presentation.welcome.WelcomeViewModel

class ViewModelFactory private constructor(
    private val lotteryRepository: LotteryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(lotteryRepository)
                isAssignableFrom(WelcomeViewModel::class.java) -> WelcomeViewModel()
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(lotteryRepository)
                isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(lotteryRepository)
                isAssignableFrom(RetrieveViewModel::class.java) -> RetrieveViewModel(lotteryRepository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    provideLotteryRepository(context)
                ).apply {
                    instance = this
                }
            }
    }

}