package com.github.haejung83.presentation.frequently

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.haejung83.databinding.ViewItemFrequentlyBinding

class FrequentlyAdapter(
    private val viewModel: FrequentlyViewModel // Just in case, for adding action listener
) : ListAdapter<FrequentlyItem, FrequentlyAdapter.FrequentlyViewHolder>(FrequentlyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FrequentlyViewHolder(
            ViewItemFrequentlyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: FrequentlyViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FrequentlyViewHolder(
        private val binding: ViewItemFrequentlyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FrequentlyItem) {
            binding.let {
                it.item = item
                it.executePendingBindings()
            }
        }
    }

    class FrequentlyDiffCallback : DiffUtil.ItemCallback<FrequentlyItem>() {
        override fun areItemsTheSame(oldItem: FrequentlyItem, newItem: FrequentlyItem) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: FrequentlyItem, newItem: FrequentlyItem) =
            oldItem == newItem
    }

}