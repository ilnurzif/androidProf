package com.prof.dz.use_case.interactors

import com.prof.dz.entities.DataModel

interface IMainInteractor<T : DataModel> {
   suspend fun getData(word: String, fromRemoteSource: Boolean): T
}