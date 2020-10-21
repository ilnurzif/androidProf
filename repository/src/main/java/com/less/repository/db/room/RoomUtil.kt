package com.less.repository.db.room


import com.less.model.Meanings
import com.less.model.SearchResult
import com.less.model.Translation

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResult> {
    val searchList = ArrayList<SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            entity.description
            val mearning = Meanings(
               Translation(entity.description),
                entity.imageurl
            )
            val meanings = listOf(mearning)
            searchList.add(SearchResult(entity.word, meanings))
        }
    }
    return searchList
}

fun searchResultToHistoryEntitie(data: List<SearchResult>) : HistoryEntity? {
  val word=data.get(0).text
  val description = data.get(0).meanings?.get(0)?.translation?.translation
  val imageurl = data.get(0).meanings?.get(0)?.imageUrl
  val h= HistoryEntity(word!!, description, imageurl)
  return data.get(0).text?.let {h}
}
