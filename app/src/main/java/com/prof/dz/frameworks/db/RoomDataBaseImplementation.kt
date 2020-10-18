package com.prof.dz.frameworks.db

import com.prof.dz.frameworks.db.room.*
import com.prof.dz.frameworks.network.model.SearchResult

/* class RoomDataBaseImplementation(private val historyDao: HistoryDao) : IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        //return mapHistoryEntityToSearchResult(historyDao.all())
        val l = listOf(SearchResult("",null))
        return l
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
       // searchResultToHistoryEntitie(data)?.let { historyDao.insert(it) }
    }
}*/

class RoomDataBaseImplementation(private val historyDao: HistoryDao) : IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
    return if (word!="") {mapHistoryEntityToSearchResult(historyDao.getDataByWord(word))}
               else {mapHistoryEntityToSearchResult(historyDao.all())}
   /*     val l = listOf(SearchResult("",null))
        return l*/
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
       searchResultToHistoryEntitie(data)?.let { historyDao.insert(it) }
       val d = data
    }
}

/*
class RoomDataBaseImplementation2(): IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        val l = listOf(SearchResult("",null))
        return l
    }

    override suspend fun saveToDB(data: List<SearchResult>) {
        // searchResultToHistoryEntitie(data)?.let { historyDao.insert(it) }
    }
}
*/


/*class RoomDataBaseImplementation() : IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        val l = listOf(SearchResult("",null))
        return l
    }

    override suspend fun saveToDB(data: List<SearchResult>) {

    }
}*/

