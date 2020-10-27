package com.prof.dz.frameworks.koin

import android.app.Application
import androidx.room.Room
import com.less.repository.db.room.HistoryDataBase
import com.less.repository.db.RoomDataBaseImplementation
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import com.less.repository.repositories.RepositoryImplementation
import com.prof.dz.application.MyApp
import com.prof.dz.frameworks.view.main.MainActivity
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


// Объявим функцию, которая будет создавать зависимости по требованию
fun injectDependencies() = loadModules
// Ленивая инициализация создаст зависимости только тогда, когда функция будет
// вызвана
private val loadModules by lazy {
    // Функция библиотеки Koin
    loadKoinModules(listOf(application, mainViewModel_))
}
// Остальное никак не изменилось
val application = module {
       single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB2").build() }
        single { get<HistoryDataBase>().historyDao() }
        single { RoomDataBaseImplementation(get()) }
        single { RepositoryImplementation(DataSourceRemote()) }
}

val mainViewModel_ = module {
/*    factory<MainInteractor> { MainInteractor(get<RepositoryImplementation>(), get<RoomDataBaseImplementation>()) }
    factory { MainViewModel(get())}*/
scope(named<MainActivity>()) {
    scoped { MainInteractor(get<RepositoryImplementation>(), get<RoomDataBaseImplementation>()) }
    scoped { MainViewModel(get())}
}
}

