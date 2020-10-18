package com.prof.dz.use_case.repositories

import com.prof.dz.frameworks.db.room.IDataSourceLocal
import com.prof.dz.frameworks.network.model.SearchResult

class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<SearchResult>>) : IRepositoryLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
        dataSource.saveToDB(data)
    }
}

