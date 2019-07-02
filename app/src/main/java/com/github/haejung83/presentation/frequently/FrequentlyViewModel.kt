package com.github.haejung83.presentation.frequently

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.presentation.base.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class FrequentlyViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    private val _items = MutableLiveData<List<FrequentlyItem>>().apply { value = emptyList() }
    val items: LiveData<List<FrequentlyItem>>
        get() = _items

    fun showSortedLotteryNumberRank() {
        lotteryRepository.getLotteries()
            .subscribeOn(Schedulers.io())
            .map { it.map { lottery -> lottery.toSixNumberList() } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val arrangedRanks = mutableListOf<FrequentlyItem>().apply {
                    val flattenLotteries = it.flatten()
                    for (number in 1..45)
                        add(FrequentlyItem(0, number, flattenLotteries.count { it == number }))
                    sortByDescending { it.count }
                }
                arrangedRanks.forEachIndexed { index, frequentlyItem -> frequentlyItem.rank = index + 1 }
                _items.value = arrangedRanks
            }
            .addTo(disposable)
    }

}