package com.prof.dz.use_case.repositories

import io.reactivex.Observable

interface IRepository<T> {
    fun getData(word: String): Observable<T>
}
