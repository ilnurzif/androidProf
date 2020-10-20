package com.less.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.less.model.DataModel
import kotlinx.coroutines.*


abstract class BaseViewModel<T : DataModel> (): ViewModel()
{
    var msgLiveData: MutableLiveData<T> = MutableLiveData()

    fun subscribe(): LiveData<T> {
        return msgLiveData
    }
    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    abstract fun handleError(throwable: Throwable)
}
