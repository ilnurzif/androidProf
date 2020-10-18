package com.prof.dz.interface_adapters.viewmodels

import com.prof.dz.entities.DataModel
import com.prof.dz.use_case.interactors.HistoryInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val historyInteractor: HistoryInteractor) : BaseViewModel<DataModel>() {
    override fun getData(word: String, isOnline: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            val v = historyInteractor.getData(word, isOnline)
            msgLiveData.postValue(v)
        }

    override fun handleError(throwable: Throwable) {
        msgLiveData.postValue(DataModel.Error(throwable))
    }
}