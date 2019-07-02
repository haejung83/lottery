package com.github.haejung83.presentation.retrieve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.haejung83.LOTTERY_DRAW_NUMBER_CACHE_LIMIT
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.presentation.base.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RetrieveViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    private val _drawNumber = MutableLiveData<Int>()
    val drawNumber: LiveData<String> = Transformations.map(_drawNumber) {
        it.toString()
    }

    private val _lotteryNumbers = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val lotteryNumbers: LiveData<String> = Transformations.map(_lotteryNumbers) {
        it.joinToString()
    }

    private val _bonusNumber = MutableLiveData<Int>()
    val bonusNumber: LiveData<String> = Transformations.map(_bonusNumber) {
        it?.toString() ?: ""
    }

    private val _isValidDrawNumber = MutableLiveData<Boolean>().apply { value = true }
    val isValidDrawNumber: LiveData<Boolean>
        get() = _isValidDrawNumber

    fun showLotteryByDrawNumber(drawNumber: Int) {
        Timber.i("showLotteryByDrawNumber: $drawNumber")
        if (checkDrawNumber(drawNumber)) {
            lotteryRepository.getLotteryByDrawNumber(drawNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _drawNumber.value = it.drawNo
                    _lotteryNumbers.value = it.toSixNumberList()
                    _bonusNumber.value = it.bonusNo
                }, {
                    _isValidDrawNumber.value = false
                })
                .addTo(disposable)

            refreshRepositoryIfNeeds()
        } else {
            _isValidDrawNumber.value = false
        }
    }

    private fun refreshRepositoryIfNeeds() {
        // In this case, don't care of UI indicating like a progress bar
        lotteryRepository.count()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != LOTTERY_DRAW_NUMBER_CACHE_LIMIT)
                    lotteryRepository.refresh()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
            }, {
                _isValidDrawNumber.value = false
            })
            .addTo(disposable)
    }

    private fun checkDrawNumber(drawNumber: Int) =
        drawNumber in 1..LOTTERY_DRAW_NUMBER_CACHE_LIMIT

}