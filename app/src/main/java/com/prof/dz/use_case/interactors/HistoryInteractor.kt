package com.prof.dz.use_case.interactors
import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.use_case.repositories.IRepositoryLocal

class HistoryInteractor ( private val repositoryLocal: IRepositoryLocal<List<SearchResult>>) : IInteractor<DataModel> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val data=repositoryLocal.getData(word)
        return DataModel.Success(data);
    }
}