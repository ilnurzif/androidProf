package com.less.core

import com.less.model.DataModel


interface IInteractor<T : DataModel> {
   suspend fun getData(word: String, fromRemoteSource: Boolean): T
}