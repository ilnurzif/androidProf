package com.less.repository.repositories

import com.less.repository.db.room.IDataSourceLocal
import com.less.model.SearchResult

class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<com.less.model.SearchResult>>) : IRepositoryLocal<List<com.less.model.SearchResult>> {

    override suspend fun getData(word: String): List<com.less.model.SearchResult> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(data: List<com.less.model.SearchResult>) {
        dataSource.saveToDB(data)
    }
}

