package com.less.repository.db.room

import com.less.model.Meanings
import com.less.model.SearchResult
import com.less.model.Translation

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<com.less.model.SearchResult> {
    val searchList = ArrayList<com.less.model.SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            entity.description
            val mearning = com.less.model.Meanings(
                com.less.model.Translation(entity.description),
                entity.imageurl
            )
            val meanings = listOf(mearning)
            searchList.add(com.less.model.SearchResult(entity.word, meanings))
        }
    }
    return searchList
}

fun searchResultToHistoryEntitie(data: List<com.less.model.SearchResult>) : HistoryEntity? {
  val word=data.get(0).text
  val description = data.get(0).meanings?.get(0)?.translation?.translation
  val imageurl = data.get(0).meanings?.get(0)?.imageUrl
  val h= HistoryEntity(word!!, description, imageurl)
  return data.get(0).text?.let {h}
}
