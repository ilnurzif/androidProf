package com.prof.dz.interface_adapters.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.dz.entities.DataModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T : DataModel> (): ViewModel()
{
    val msgLiveData: MutableLiveData<T> = MutableLiveData()
    fun subscribe(): LiveData<T> {
        return msgLiveData
    }
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    abstract fun getData(word: String, isOnline: Boolean, ioSheduler: Scheduler, uiSheduler: Scheduler)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
