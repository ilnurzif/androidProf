package com.prof.dz.frameworks.koin

import androidx.room.Room
import com.prof.dz.frameworks.db.RoomDataBaseImplementation
import com.prof.dz.frameworks.db.room.HistoryDataBase
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import com.prof.dz.use_case.repositories.RepositoryImplementation
import com.prof.dz.use_case.repositories.RepositoryImplementationLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import org.koin.dsl.module

val application = module {
/*    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao()}
    single { RoomDataBaseImplementation(get()) }*/

    single {RoomDataBaseImplementation( Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build().historyDao())}
 //   single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build().historyDao()}

  //  single {  RoomDataBaseImplementation2(get())  }
    single<MainInteractor> {
        MainInteractor(RepositoryImplementation(DataSourceRemote()),
         //   get()
       //    RepositoryImplementationLocal(RoomDataBaseImplementation2())
         // RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
         //   RepositoryImplementationLocal(RoomDataBaseImplementation(Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build().historyDao()))
             RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
        )
    }
}

val mainViewModel_ = module {
    factory { MainViewModel(get()) }
}