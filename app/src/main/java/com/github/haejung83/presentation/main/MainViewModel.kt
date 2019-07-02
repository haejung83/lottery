package com.github.haejung83.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.haejung83.LOTTERY_DRAW_NUMBER_CACHE_LIMIT
import com.github.haejung83.R
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.data.local.Lottery
import com.github.haejung83.presentation.Event
import com.github.haejung83.presentation.base.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class MainViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    val cachedDrawCount = LOTTERY_DRAW_NUMBER_CACHE_LIMIT

    private val _openScreenEvent = MutableLiveData<Event<OpenScreen>>()
    val openScreenEvent: LiveData<Event<OpenScreen>>
        get() = _openScreenEvent

    private val _showWinResultEvent = MutableLiveData<Event<Int>>()
    val showWinResultEvent: LiveData<Event<Int>>
        get() = _showWinResultEvent

    private val _generatedLotteryNumbers = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val generatedLotteryNumbers: LiveData<String> = Transformations.map(_generatedLotteryNumbers) {
        it.joinToString()
    }

    // Bi-directional
    val drawNumber = MutableLiveData<String>()

    // Check a validation of draw number field
    val isSatisfiedDrawNumberField: LiveData<Boolean> = Transformations.map(drawNumber) {
        it.isNotEmpty() && it.toInt() in 1..LOTTERY_DRAW_NUMBER_CACHE_LIMIT
    }

    // Check validation both draw number and generated number
    val isSatisfiedDrawNumberAndGenerateNumbers: MediatorLiveData<Boolean> = MediatorLiveData()

    // Build a MediatorLiveData for checking validation
    init {
        isSatisfiedDrawNumberAndGenerateNumbers.addSource(_generatedLotteryNumbers) {
            isSatisfiedDrawNumberAndGenerateNumbers.value =
                it.isNotEmpty() && isSatisfiedDrawNumberField.value ?: false
        }
        isSatisfiedDrawNumberAndGenerateNumbers.addSource(isSatisfiedDrawNumberField) {
            isSatisfiedDrawNumberAndGenerateNumbers.value =
                it && _generatedLotteryNumbers.value?.isNotEmpty() ?: false
        }
    }

    fun generateLottery() {
        _generatedLotteryNumbers.value = generateRandomSevenDigits()
    }

    private fun generateRandomSevenDigits() =
        mutableSetOf<Int>().apply {
            while (size < 6) add(Random.nextInt(1, 46))
        }.sorted()

    fun checkGeneratedLottery() {
        drawNumber.value?.let {
            lotteryRepository.getLotteryByDrawNumber(it.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lottery ->
                    checkWinAndShowResult(lottery)
                }
        }
    }

    private fun checkWinAndShowResult(lottery: Lottery) {
        val generatedNumbers = _generatedLotteryNumbers.value ?: emptyList()
        val winNumbers = lottery.toSixNumberList()
        val winBonusNumber = lottery.bonusNo

        val winResultMessage: Int = when (winNumbers.count { generatedNumbers.contains(it) }) {
            6 -> R.string.label_win_dialog_1st
            5 -> if (generatedNumbers.contains(winBonusNumber)) R.string.label_win_dialog_2nd else R.string.label_win_dialog_3rd
            4 -> R.string.label_win_dialog_4th
            3 -> R.string.label_win_dialog_5th
            else -> R.string.label_win_dialog_losing
        }
        _showWinResultEvent.value = Event(winResultMessage)
    }

    fun openHistory() {
        _openScreenEvent.value = Event(OpenScreen.OpenHistoryScreen)
    }

    fun openFrequently() {
        _openScreenEvent.value = Event(OpenScreen.OpenFrequentlyScreen)
    }

    sealed class OpenScreen {
        object OpenHistoryScreen : OpenScreen()
        object OpenFrequentlyScreen : OpenScreen()
    }

}
