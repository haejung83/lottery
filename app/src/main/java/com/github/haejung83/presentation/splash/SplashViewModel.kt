package com.github.haejung83.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.haejung83.LOTTERY_DRAW_NUMBER_CACHE_LIMIT
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.presentation.Event
import com.github.haejung83.presentation.base.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SplashViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    private val _moveToEvent = MutableLiveData<Event<MoveToClass>>()
    val moveToEvent: LiveData<Event<MoveToClass>>
        get() = _moveToEvent

    private val _refreshErrorEvent = MutableLiveData<Event<String>>()
    val refreshErrorEvent: LiveData<Event<String>>
        get() = _refreshErrorEvent

    private val _refreshDataLoading = MutableLiveData<Boolean>().apply { value = false }
    val refreshDataLoading: LiveData<Boolean>
        get() = _refreshDataLoading

    fun checkLotteryAndRefreshIfNeeds() {
        lotteryRepository.count()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ count ->
                Timber.i("Count: $count")
                if (count != LOTTERY_DRAW_NUMBER_CACHE_LIMIT) {
                    refreshLotteries()
                } else {
                    _moveToEvent.value = Event(MoveToClass.MoveToMain)
                }
            }, {
                Timber.e(it)
                _refreshErrorEvent.value = Event(it.localizedMessage)
            })
            .addTo(disposable)
    }

    private fun refreshLotteries() {
        lotteryRepository.refresh()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _refreshDataLoading.value = true }
            .doOnComplete { _refreshDataLoading.value = false }
            .subscribe({
                Timber.i("Refresh: Done")
                _moveToEvent.value = Event(MoveToClass.MoveToWelcome)
            }, {
                Timber.e(it)
                _refreshErrorEvent.value = Event(it.localizedMessage)
            })
            .addTo(disposable)
    }

    sealed class MoveToClass {
        object MoveToMain : MoveToClass()
        object MoveToWelcome : MoveToClass()
    }

}