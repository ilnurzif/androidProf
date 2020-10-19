package com.prof.dz.frameworks.db

import com.prof.dz.frameworks.db.room.*
import com.prof.dz.frameworks.network.model.SearchResult


 class RoomDataBaseImplementation(val historyDao: HistoryDao) : IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
    return if (word!="") {mapHistoryEntityToSearchResult(historyDao.getDataByWord(word))}
               else {mapHistoryEntityToSearchResult(historyDao.all())}
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
       searchResultToHistoryEntitie(data)?.let { historyDao.insert(it) }
       val d = data
    }
}



