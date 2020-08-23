package com.example.searchviewwithrxjava

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchviewwithrxjava.util.HistorySharedPreferences

class MainViewModelFactory(private val historySharedPreferences: HistorySharedPreferences) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(historySharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}