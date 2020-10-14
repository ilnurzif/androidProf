package com.prof.dz.interface_adapters.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.dz.entities.DataModel
import kotlinx.coroutines.*

abstract class BaseViewModel<T : DataModel> (): ViewModel()
{
    val msgLiveData: MutableLiveData<T> = MutableLiveData()

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
