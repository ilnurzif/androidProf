package com.less.historyscreen.frameworks.view

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prof.dz.R
import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.frameworks.view.BaseActivity
import com.less.historyscreen.frameworks.view.adapter.HistoryAdapter
import com.prof.dz.interface_adapters.viewmodels.HistoryViewModel
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.HistoryInteractor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryActivity() : BaseActivity<DataModel, HistoryInteractor>() {
    override lateinit var viewModel: MainViewModel
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
            main_activity_recyclerview.layoutManager =
                LinearLayoutManager(applicationContext)
            main_activity_recyclerview.adapter =
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
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.VISIBLE
        error_linear_layout.visibility = android.view.View.GONE
    }

    override fun showErrorScreen(error: String?) {
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.VISIBLE
    }

    override fun showViewSuccess() {
    }
}