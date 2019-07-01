package com.github.haejung83.presentation.history

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.github.haejung83.data.local.Lottery

object HistoryListBindings {

    @BindingAdapter("viewModel")
    @JvmStatic
    fun setAdapterWithViewModel(recyclerView: RecyclerView, viewModel: HistoryViewModel) {
        recyclerView.adapter = HistoryAdapter(viewModel)
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: PagedList<Lottery>?) {
        (recyclerView.adapter as? HistoryAdapter)?.submitList(items)
    }

}