package com.example.searchviewwithrxjava.util

import androidx.appcompat.widget.SearchView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object RxSearchObservable {
    fun fromView(searchView: SearchView): Observable<SearchQuery> {
        val subject = PublishSubject.create<SearchQuery>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onNext(SearchQuery.Submit(s))
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(SearchQuery.TextChange(text))
                return true
            }
        })
        return subject
    }
}

sealed class SearchQuery {
    abstract val query: String

    data class Submit(override val query: String) : SearchQuery()
    data class TextChange(override val query: String) : SearchQuery()
}