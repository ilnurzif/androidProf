package geekbrains.ru.translator.model.datasource

import com.prof.dz.frameworks.network.RetrofitImplementation
import com.prof.dz.frameworks.network.model.SearchResult
import com.prof.dz.interface_adapters.data.IDataSource
import io.reactivex.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        return remoteProvider.getData(word)
    }
}
