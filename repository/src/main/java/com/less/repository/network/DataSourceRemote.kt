package geekbrains.ru.translator.model.datasource

import com.less.repository.network.RetrofitImplementation
import com.less.model.SearchResult
import com.less.repository.data.IDataSource


class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<com.less.model.SearchResult> {
        return remoteProvider.getData(word)
    }
}
