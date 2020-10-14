package geekbrains.ru.translator.model.datasource

import com.prof.dz.frameworks.db.RoomDataBaseImplementation
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> = remoteProvider.getData(word)

}
