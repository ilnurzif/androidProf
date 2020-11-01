package com.less.repository.repositories

import com.less.model.SearchResult
import com.less.repository.db.room.IDataSourceLocal


class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<SearchResult>>) : IRepositoryLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
        dataSource.saveToDB(data)
    }
}

