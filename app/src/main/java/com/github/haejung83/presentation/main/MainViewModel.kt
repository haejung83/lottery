package com.github.haejung83.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.haejung83.LOTTERY_DRAW_NUMBER_CACHE_LIMIT
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.presentation.Event
import com.github.haejung83.presentation.base.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.random.Random

class MainViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    val cachedDrawCount = LOTTERY_DRAW_NUMBER_CACHE_LIMIT

    private val _openScreenEvent = MutableLiveData<Event<OpenScreen>>()
    val openScreenEvent: LiveData<Event<OpenScreen>>
        get() = _openScreenEvent

    private val _generatedLotteryNumbers = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val generatedLotteryNumbers: LiveData<String> = Transformations.map(_generatedLotteryNumbers) {
        it.joinToString()
    }

    private val _generatedBonusNumber = MutableLiveData<Int>().apply { value = 0 }
    val generatedBonusNumber: LiveData<String> = Transformations.map(_generatedBonusNumber) {
        if (it != 0) it.toString() else ""
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
        val store = generateRandomSevenDigits()
        val (sixDigits, bonus) = pickBonusNumber(store)
        _generatedLotteryNumbers.value = sixDigits
        _generatedBonusNumber.value = bonus
    }

    private fun generateRandomSevenDigits() =
        mutableSetOf<Int>().apply {
            while (size < 7) add(Random.nextInt(1, 46))
        }.sorted()

    private fun pickBonusNumber(generatedNumber: List<Int>): Pair<List<Int>, Int> {
        val bonus = generatedNumber[Random.nextInt(7)]
        return Pair(generatedNumber.filterNot { it == bonus }, bonus)
    }

    fun checkGeneratedLottery() {
        drawNumber.value?.let {
            lotteryRepository.getLotteryByDrawNumber(it.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Timber.i("Selected Lottery: $it")
                }
        }
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
