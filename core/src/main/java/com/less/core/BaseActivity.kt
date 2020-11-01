package com.less.core

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.less.model.DataModel
import com.less.model.SearchResult
import com.less.utills.OnlineLiveData

abstract class BaseActivity<T : DataModel, I : IInteractor<T>> : AppCompatActivity() {
    protected var isNetworkAvailable: Boolean = true
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
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        val online=OnlineLiveData(this)
        isNetworkAvailable=online.networkEnabled()
            online.observe(
            this@BaseActivity,
            Observer<Boolean> {
                isNetworkAvailable = it
                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this@BaseActivity,
                        getString(R.string.device_is_offline),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

    }

}

