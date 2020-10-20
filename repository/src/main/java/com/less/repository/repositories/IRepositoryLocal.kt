package com.less.repository.repositories

import com.less.model.SearchResult

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDB(searchResultList: List<com.less.model.SearchResult>)
}