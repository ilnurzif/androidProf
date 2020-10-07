package com.prof.dz.interface_adapters.data

import io.reactivex.Observable

interface IDataSource<T> {
    fun getData(word: String): Observable<T>
}