package com.prof.dz.use_case.repositories

import com.prof.dz.frameworks.network.model.SearchResult

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDB(searchResultList: List<SearchResult>)
}