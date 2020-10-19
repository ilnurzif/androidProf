package com.prof.dz.frameworks.db.room

import com.prof.dz.frameworks.network.model.Meanings
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.frameworks.network.model.Translation

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResult> {
    val searchList = ArrayList<SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            entity.description
            val mearning = Meanings(Translation(entity.description), entity.imageurl)
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
