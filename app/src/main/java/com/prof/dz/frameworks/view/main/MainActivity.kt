package com.prof.dz.frameworks.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.less.core.BaseActivity
import com.less.historyscreen.frameworks.view.HistoryActivity
import com.less.model.DataModel
import com.less.model.SearchResult
import com.prof.dz.R
import com.prof.dz.frameworks.view.WordDescrActivity
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity() : BaseActivity<DataModel, MainInteractor>() {
    private var adapter: MainAdapter? = null
    override lateinit var viewModel: MainViewModel

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.less.model.SearchResult) {
                startActivity(
                    WordDescrActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        data.meanings?.get(0)?.translation?.translation!!,
                        data.meanings?.get(0)?.imageUrl
                    ))
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return true
    }


    fun search(isOnline:Boolean) {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object :
            SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getData(searchWord, isOnline)
            }
        })
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_fab.setOnClickListener {
            search(true)
        }

        search_history_fab.setOnClickListener {
            search(false)
        }


       val tempViewModel: MainViewModel by viewModel()
        viewModel = tempViewModel

//        val mainInteractor = MainInteractor(RepositoryImplementation(DataSourceRemote()), RoomDataBaseImplementation(dao))
//        viewModel = MainViewModel(mainInteractor)
       viewModel.subscribe().observe(this@MainActivity, Observer<DataModel> { renderData(it) })
    }


    override fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
        }
    }

    override fun showViewSuccess() {
        success_linear_layout.visibility = android.view.View.VISIBLE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.GONE
    }

     override fun showViewLoading() {
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

    override fun setDataToAdapter(searchResult: List<SearchResult>) {
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

    override fun progeressVisualization(dataModel: DataModel.Loading) {
        if (dataModel.progress != null) {
            progress_bar_horizontal.visibility = android.view.View.VISIBLE
            progress_bar_round.visibility = android.view.View.GONE
            progress_bar_horizontal.progress = dataModel.progress!!
        } else {
            progress_bar_horizontal.visibility = android.view.View.GONE
            progress_bar_round.visibility = android.view.View.VISIBLE
        }
    }

}