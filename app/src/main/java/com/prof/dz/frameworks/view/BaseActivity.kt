package com.prof.dz.frameworks.view

import androidx.appcompat.app.AppCompatActivity
import com.prof.dz.R
import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.viewmodels.BaseViewModel
import com.prof.dz.use_case.interactors.IInteractor
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity<T : DataModel, I : IInteractor<T>> : AppCompatActivity() {
    protected abstract val viewModel: BaseViewModel<T>

    abstract fun setDataToAdapter(data: List<SearchResult>)
    abstract fun showViewLoading()
    protected abstract fun showErrorScreen(error: String?)
    protected abstract fun showViewSuccess()

    protected fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data
                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    setDataToAdapter(searchResult)
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

}

