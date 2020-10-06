package com.prof.dz.use_case.interactors

import io.reactivex.Observable

interface IMainInteractor<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}