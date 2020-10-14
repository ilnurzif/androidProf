package com.prof.dz.frameworks.db

import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        TODO("Not yet implemented")
    }
}
