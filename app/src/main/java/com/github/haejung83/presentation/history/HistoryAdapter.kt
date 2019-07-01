package com.github.haejung83.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.haejung83.data.local.Lottery
import com.github.haejung83.databinding.ViewItemHistoryBinding

class HistoryAdapter(
    private val viewModel: HistoryViewModel
) : PagedListAdapter<Lottery, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryViewHolder(
            ViewItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class HistoryViewHolder(
        private val binding: ViewItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lottery: Lottery) {
            binding.let {
                it.lottery = lottery
                it.executePendingBindings()
            }
        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<Lottery>() {
        override fun areItemsTheSame(oldItem: Lottery, newItem: Lottery): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Lottery, newItem: Lottery): Boolean =
            oldItem == newItem

    }
}
