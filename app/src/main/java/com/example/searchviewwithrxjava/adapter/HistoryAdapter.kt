package com.example.searchviewwithrxjava.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.history_item.*

class HistoryAdapter(private var histories: List<String>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var onLongClickHistory: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(com.example.searchviewwithrxjava.R.layout.history_item, parent, false)
        return HistoryViewHolder(containerView)
    }

    override fun getItemCount() = histories.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = histories[position]
        holder.bind(history)
    }

    fun setOnLongClickListener(listener: (Int) -> Unit) {
        onLongClickHistory = listener
    }

    fun updateHistory(newHistories: List<String>) {
        val diffCallback = HistoryDiffCallback(histories, newHistories)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        histories = newHistories
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(history: String) {
            text_history.text = history
            text_history.setOnLongClickListener {
                onLongClickHistory?.invoke(adapterPosition)
                true
            }
        }
    }
}
