package com.prof.dz.interface_adapters.viewmodels

import com.less.core.BaseViewModel
import com.less.model.DataModel
import com.prof.dz.use_case.interactors.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val mainInteractor: MainInteractor) : BaseViewModel<DataModel>() {

    override fun getData(word: String, isOnline: Boolean) {
        msgLiveData.value =  DataModel.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            val v =mainInteractor.getData(word, isOnline)
            msgLiveData.postValue(v)
        }

    override fun handleError(throwable: Throwable) {
        msgLiveData.postValue(DataModel.Error(throwable))
    }
}