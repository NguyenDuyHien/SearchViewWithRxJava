package com.example.searchviewwithrxjava.adapter

import androidx.recyclerview.widget.DiffUtil

class HistoryDiffCallback(private val oldHistory: List<String>, private val newHistory: List<String>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldHistory.size

    override fun getNewListSize() = newHistory.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHistory[oldItemPosition] == newHistory[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHistory[oldItemPosition] == newHistory[newItemPosition]
    }
}