package com.prof.dz.interface_adapters.presenters

import com.prof.dz.entities.DataModel
import com.prof.dz.use_case.interactors.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<DataModel>() {

    override fun getData(word: String, isOnline: Boolean) {
        msgLiveData.value = DataModel.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            msgLiveData.postValue(interactor.getData(word, isOnline))
        }

    override fun handleError(throwable: Throwable) {
        msgLiveData.postValue(DataModel.Error(throwable))
    }
}