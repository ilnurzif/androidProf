package com.prof.dz.use_case.interactors

import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.use_case.repositories.IRepository

class MainInteractor (
      val remoteRepository: IRepository<List<SearchResult>>,
      val localRepository: IRepository<List<SearchResult>>,
) : IMainInteractor<DataModel> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val data=if (fromRemoteSource) {
            remoteRepository.getData(word)
        } else {
            localRepository.getData(word)}
        return DataModel.Success(data);
        }
    }



