package com.prof.dz.frameworks.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prof.dz.R
import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.presenters.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity() : BaseActivity<DataModel, MainInteractor>() {
    private var adapter: MainAdapter? = null
    override lateinit var mainViewModel: MainViewModel

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    mainViewModel.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        val tempViewModel: MainViewModel by viewModel()
        mainViewModel = tempViewModel
        mainViewModel.subscribe().observe(this@MainActivity, Observer<DataModel> { renderData(it) })
    }

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data
                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter =
                            MainAdapter(onListItemClickListener, searchResult)
                    } else {
                        adapter!!.setData(searchResult)
                    }
                }
            }
            is DataModel.Loading -> {
                showViewLoading()
                if (dataModel.progress != null) {
                    progress_bar_horizontal.visibility = android.view.View.VISIBLE
                    progress_bar_round.visibility = android.view.View.GONE
                    progress_bar_horizontal.progress = dataModel.progress
                } else {
                    progress_bar_horizontal.visibility = android.view.View.GONE
                    progress_bar_round.visibility = android.view.View.VISIBLE
                }
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = android.view.View.VISIBLE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.VISIBLE
        error_linear_layout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}