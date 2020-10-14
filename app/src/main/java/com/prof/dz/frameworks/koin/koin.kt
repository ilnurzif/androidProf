package com.prof.dz.frameworks.koin

import com.prof.dz.interface_adapters.presenters.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import com.prof.dz.use_case.repositories.RepositoryImplementation
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import org.koin.dsl.module

val application = module {
   single<MainInteractor> {
       MainInteractor(RepositoryImplementation(DataSourceRemote()), RepositoryImplementation(DataSourceLocal()))
   }
}

val mainViewModel_ = module {
    factory { MainViewModel(get()) }
}