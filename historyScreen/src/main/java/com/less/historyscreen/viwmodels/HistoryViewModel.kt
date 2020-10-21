package com.less.historyscreen.viwmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryViewModel(private val historyInteractor: com.less.historyscreen.use_case.HistoryInteractor) : com.less.core.BaseViewModel<com.less.model.DataModel>() {
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
        msgLiveData.postValue(com.less.model.DataModel.Error(throwable))
    }
}