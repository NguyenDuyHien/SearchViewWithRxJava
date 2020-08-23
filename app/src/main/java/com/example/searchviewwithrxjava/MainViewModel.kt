package com.example.searchviewwithrxjava

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchviewwithrxjava.history.History
import com.example.searchviewwithrxjava.util.HistorySharedPreferences
import com.example.searchviewwithrxjava.util.SearchQuery
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class MainViewModel(private val historySharedPreferences: HistorySharedPreferences) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private val _history = MutableLiveData<History>()
    val history: LiveData<History> = _history

    init {
        _history.postValue(historySharedPreferences.getHistory())
    }

    fun subscribeRxSearchObservable(rxSearch: Observable<SearchQuery>) {
        addDisposable(rxSearch
                .subscribe({ searchQuery ->
                    if (searchQuery is SearchQuery.Submit) {
                        _result.postValue(searchQuery.query)
                        _history.postValue(historySharedPreferences.saveHistory(searchQuery.query))
                    }
                }, {})
        )
    }

    fun deleteHistory(position: Int) {
        _history.postValue(historySharedPreferences.deleteHistory(position))
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}