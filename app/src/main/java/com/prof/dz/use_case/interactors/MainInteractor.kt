package com.prof.dz.use_case.interactors

import com.less.repository.db.RoomDataBaseImplementation
import com.less.repository.repositories.IRepository

class MainInteractor (
    val remoteRepository: IRepository<List<com.less.model.SearchResult>>,
    val localRepository: RoomDataBaseImplementation
) : com.less.core.IInteractor<com.less.model.DataModel> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.less.model.DataModel {
        val data=if (fromRemoteSource) {
            remoteRepository.getData(word)
        } else {
            localRepository.getData(word)}
        if (fromRemoteSource) localRepository.saveToDB(data);
         return com.less.model.DataModel.Success(data);
       }
    }



