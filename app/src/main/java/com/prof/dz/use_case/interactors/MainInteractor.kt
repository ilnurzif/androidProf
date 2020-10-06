package com.prof.dz.use_case.interactors

import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.use_case.repositories.IRepository
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: IRepository<List<SearchResult>>,
    private val localRepository: IRepository<List<SearchResult>>
) : IMainInteractor<DataModel> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { DataModel.Success(it) }
        } else {
            localRepository.getData(word).map { DataModel.Success(it) }
        }
    }
}