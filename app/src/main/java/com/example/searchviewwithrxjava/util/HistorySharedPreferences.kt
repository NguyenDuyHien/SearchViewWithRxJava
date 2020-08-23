package com.example.searchviewwithrxjava.util

import android.content.Context
import com.example.searchviewwithrxjava.history.History
import com.google.gson.Gson

class HistorySharedPreferences(context: Context) {
    private var pref = context.getSharedPreferences(HISTORY_PREFS, Context.MODE_PRIVATE)

    fun saveHistory(query: String): History {
        val newList = listOf(query) + getHistory().list
        val newHistory = History(newList.distinct())
        with(newHistory) {
            pref.edit().putString(HISTORY_KEY, Gson().toJson(this)).apply()
            return this
        }
    }

    fun getHistory(): History {
        val historyJson = pref.getString(HISTORY_KEY, "{}")
        return Gson().fromJson(historyJson, History::class.java)
    }

    fun deleteHistory(position: Int): History {
        val oldList = getHistory().list.toMutableList()
        oldList.removeAt(position)
        val newHistory = History(oldList)
        with(newHistory) {
            pref.edit().putString(HISTORY_KEY, Gson().toJson(this)).apply()
            return this
        }
    }

    companion object {
        const val HISTORY_PREFS = "HISTORY_PREFS"
        const val HISTORY_KEY = "HISTORY"
    }
}