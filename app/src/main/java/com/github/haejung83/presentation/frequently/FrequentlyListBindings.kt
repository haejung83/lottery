package com.github.haejung83.presentation.frequently

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object FrequentlyListBindings {

    @BindingAdapter("viewModel")
    @JvmStatic
    fun setAdapterWithViewModel(recyclerView: RecyclerView, viewModel: FrequentlyViewModel) {
        recyclerView.adapter = FrequentlyAdapter(viewModel)
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<FrequentlyItem>?) {
        (recyclerView.adapter as? FrequentlyAdapter)?.submitList(items)
    }

}