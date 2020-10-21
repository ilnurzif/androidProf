package com.less.historyscreen.frameworks.view

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.less.core.BaseActivity
import com.less.historyscreen.R
import com.less.historyscreen.frameworks.view.adapter.HistoryAdapter
import com.less.historyscreen.use_case.HistoryInteractor
import com.less.historyscreen.viwmodels.HistoryViewModel
import com.less.model.DataModel
import com.less.model.SearchResult
//import kotlinx.android.synthetic.main.history_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryActivity() : BaseActivity<DataModel, HistoryInteractor>() {
    override lateinit var viewModel: HistoryViewModel
    lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private lateinit var rw :RecyclerView

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
            rw.layoutManager = LinearLayoutManager(applicationContext)
            rw.adapter = HistoryAdapter()
   /*         history_activity_RW.layoutManager =
                LinearLayoutManager(applicationContext)
            history_activity_RW.adapter =
                HistoryAdapter()*/
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
   //     if (history_activity_RW.adapter != null) {
        if (rw.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<DataModel> { renderData(it) })
    }

    private fun initViews() {
        var rw=findViewById<RecyclerView>(R.id.history_activity_RW)
        rw.adapter = adapter
    //    history_activity_RW.adapter = adapter
    }

    override fun showViewLoading() {
     /*   success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.VISIBLE
        error_linear_layout.visibility = android.view.View.GONE*/
    }

    override fun showErrorScreen(error: String?) {
   /*     success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.VISIBLE*/
    }

    override fun showViewSuccess() {
    }

    override fun progeressVisualization(dataModel: DataModel.Loading) {
        TODO("Not yet implemented")
    }
}