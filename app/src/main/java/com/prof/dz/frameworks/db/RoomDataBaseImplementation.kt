package com.prof.dz.frameworks.db

import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : IDataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
