package com.prof.dz.use_case.repositories

interface IRepository<T> {
    suspend fun getData(word: String): T
}
