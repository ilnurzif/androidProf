package com.less.historyscreen.use_case
import com.less.model.DataModel
import com.less.repository.db.RoomDataBaseImplementation

class HistoryInteractor (
    private val repositoryLocal: RoomDataBaseImplementation
) : com.less.core.IInteractor<com.less.model.DataModel> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.less.model.DataModel {
        val data=repositoryLocal.getData(word)
        return com.less.model.DataModel.Success(data);
    }
}