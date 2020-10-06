package com.prof.dz.entities

import com.prof.dz.frameworks.network.model.SearchResult

sealed class DataModel {
    data class Success(val data: List<SearchResult>) : DataModel()
    data class Error(val error: Throwable) : DataModel()
    data class Loading(val progress: Int?) : DataModel()
}
