package com.example.searchviewwithrxjava

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchviewwithrxjava.adapter.HistoryAdapter
import com.example.searchviewwithrxjava.history.History
import com.example.searchviewwithrxjava.util.HistorySharedPreferences
import com.example.searchviewwithrxjava.util.RxSearchObservable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var historySharedPreferences: HistorySharedPreferences
    private lateinit var mainViewModel: MainViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        historySharedPreferences = HistorySharedPreferences(this)
        val mainViewModelFactory = MainViewModelFactory(historySharedPreferences)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        setUpRxSearch()
        historyAdapter = HistoryAdapter(listOf())
        historyAdapter.setOnLongClickListener { position ->
            mainViewModel.deleteHistory(position)
        }

        recycler_view_history.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = historyAdapter
        }

        mainViewModel.result.observe(this, Observer<String> {
            Toast.makeText(this, "Called request for $it", Toast.LENGTH_SHORT).show()
        })

        mainViewModel.history.observe(this, Observer<History> { history ->
            historyAdapter.updateHistory(history.list)
        })
    }

    private fun setUpRxSearch() {
        mainViewModel.subscribeRxSearchObservable(
                RxSearchObservable.fromView(search_view)
//                .debounce(300, TimeUnit.MILLISECONDS)
//                .filter {
//                    return@filter it.query.isNotEmpty()
//                }
//                .distinctUntilChanged()
        )
    }
}
