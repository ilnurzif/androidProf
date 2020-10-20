package com.less.repository.repositories

interface IRepository<T> {
    suspend fun getData(word: String): T
}
