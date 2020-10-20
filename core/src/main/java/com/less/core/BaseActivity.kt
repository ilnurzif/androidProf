package com.less.core

import androidx.appcompat.app.AppCompatActivity
import com.less.model.DataModel
import com.less.model.SearchResult

abstract class BaseActivity<T : DataModel, I : IInteractor<T>> : AppCompatActivity() {
    protected abstract val viewModel: BaseViewModel<T>

    abstract fun setDataToAdapter(data: List<SearchResult>)
    abstract fun showViewLoading()
    protected abstract fun showErrorScreen(error: String?)
    protected abstract fun showViewSuccess()
    protected abstract fun progeressVisualization(dataModel: DataModel.Loading)

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
                progeressVisualization(dataModel)
      /*         if (dataModel.progress != null) {
                    progress_bar_horizontal.visibility = android.view.View.VISIBLE
                    progress_bar_round.visibility = android.view.View.GONE
                    progress_bar_horizontal.progress = dataModel.progress
                } else {
                    progress_bar_horizontal.visibility = android.view.View.GONE
                    progress_bar_round.visibility = android.view.View.VISIBLE
                } */
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

}

