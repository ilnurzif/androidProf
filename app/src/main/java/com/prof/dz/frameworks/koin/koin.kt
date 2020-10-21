package com.prof.dz.frameworks.koin

import androidx.room.Room
import com.less.historyscreen.use_case.HistoryInteractor
import com.less.repository.db.room.HistoryDataBase
import com.less.historyscreen.viwmodels.HistoryViewModel
import com.less.repository.db.RoomDataBaseImplementation
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import com.less.repository.repositories.RepositoryImplementation
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import org.koin.dsl.module.module


val application = module {
   single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB2").build() }
//   single { Room.databaseBuilder(androidApplication(), HistoryDataBase::class.java, "HistoryDB2").build() }
   single { get<HistoryDataBase>().historyDao()}
   single{ RoomDataBaseImplementation(get()) }
   single{RepositoryImplementation(DataSourceRemote())}
}

val mainViewModel_ = module {
   factory<MainInteractor> { MainInteractor(get<RepositoryImplementation>(), get<RoomDataBaseImplementation>()) }
   factory { MainViewModel(get())}
}

val historyViewModel_ = module {
    factory<HistoryInteractor> {
        HistoryInteractor(
            get<RoomDataBaseImplementation>()
        )
    }
    factory { HistoryViewModel(get()) }
}