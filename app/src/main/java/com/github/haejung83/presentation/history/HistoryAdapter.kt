package com.github.haejung83.presentation.history

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.haejung83.data.local.Lottery

class HistoryAdapter(
    private val viewModel: HistoryViewModel
) : PagedListAdapter<Lottery, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        TODO("not implemented")
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class HistoryViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lottery: Lottery) {

        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<Lottery>() {
        override fun areItemsTheSame(oldItem: Lottery, newItem: Lottery): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Lottery, newItem: Lottery): Boolean =
            oldItem == newItem

    }
}
