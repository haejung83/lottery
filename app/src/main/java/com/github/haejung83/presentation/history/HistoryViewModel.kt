package com.github.haejung83.presentation.history

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.haejung83.data.LotteryRepository
import com.github.haejung83.data.local.Lottery
import com.github.haejung83.presentation.base.DisposableViewModel

class HistoryViewModel(
    private val lotteryRepository: LotteryRepository
) : DisposableViewModel() {

    val items: LiveData<PagedList<Lottery>> =
        LivePagedListBuilder(lotteryRepository.getLotteriesForPaging(), 10).build()

}