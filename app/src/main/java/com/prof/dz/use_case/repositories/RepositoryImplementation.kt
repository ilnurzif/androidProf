package com.prof.dz.use_case.repositories

import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource

class RepositoryImplementation(private val dataSource: IDataSource<List<SearchResult>>) : IRepository<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}
