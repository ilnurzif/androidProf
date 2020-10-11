package com.prof.dz.use_case.interactors

import com.prof.dz.entities.DataModel
import io.reactivex.Observable

interface IMainInteractor<T : DataModel> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}