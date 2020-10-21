package com.less.historyscreen.frameworks.view

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.less.core.BaseActivity
import com.less.historyscreen.R
import com.less.historyscreen.frameworks.view.adapter.HistoryAdapter
import com.less.historyscreen.use_case.HistoryInteractor
import com.less.historyscreen.viwmodels.HistoryViewModel
import com.less.model.DataModel
import com.less.model.SearchResult
import kotlinx.android.synthetic.main.history_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryActivity() : BaseActivity<DataModel, HistoryInteractor>() {
    override lateinit var viewModel: HistoryViewModel
    lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        setActionbarHomeButtonAsUp()
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(searchResult: List<SearchResult>) {
        showViewSuccess()
        if (adapter == null) {
           history_activity_RW.layoutManager =
                LinearLayoutManager(applicationContext)
            history_activity_RW.adapter =
                HistoryAdapter()
        } else {
            adapter!!.setData(searchResult)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun iniViewModel() {
     if (history_activity_RW.adapter != null) {
           throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<DataModel> { renderData(it) })
    }

    private fun initViews() {
        history_activity_RW.adapter = adapter
    }

    override fun showViewLoading() {
    }

    override fun showErrorScreen(error: String?) {

    }

    override fun showViewSuccess() {
    }

    override fun progeressVisualization(dataModel: DataModel.Loading) {

    }
}