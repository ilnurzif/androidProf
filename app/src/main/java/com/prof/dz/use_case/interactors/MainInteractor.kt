package com.prof.dz.use_case.interactors

import com.prof.dz.di.NAME_LOCAL
import com.prof.dz.di.NAME_REMOTE
import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.use_case.repositories.IRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: IRepository<List<SearchResult>>,
    @Named(NAME_LOCAL) val localRepository: IRepository<List<SearchResult>>,
) : IMainInteractor<DataModel> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { DataModel.Success(it) }
        } else {
            localRepository.getData(word).map { DataModel.Success(it) }
        }
    }
}


