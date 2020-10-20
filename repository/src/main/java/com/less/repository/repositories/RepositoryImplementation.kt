package com.less.repository.repositories

import com.less.model.SearchResult
import com.less.repository.data.IDataSource

class RepositoryImplementation(private val dataSource: IDataSource<List<SearchResult>>) : IRepository<List<com.less.model.SearchResult>> {
    override suspend fun getData(word: String): List<com.less.model.SearchResult> {
        return dataSource.getData(word)
    }
}
