package com.prof.dz.use_case.repositories

import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: IDataSource<List<SearchResult>>) :
    IRepository<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }
}
