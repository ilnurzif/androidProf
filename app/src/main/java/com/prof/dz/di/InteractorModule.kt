package com.prof.dz.di


import com.prof.dz.use_case.interactors.MainInteractor
 import com.prof.dz.use_case.repositories.RepositoryImplementation
 import dagger.Module
import dagger.Provides
 import geekbrains.ru.translator.model.datasource.DataSourceLocal
 import geekbrains.ru.translator.model.datasource.DataSourceRemote

@Module
class InteractorModule {
    @Provides
    fun provideInteractor() = MainInteractor(RepositoryImplementation(DataSourceRemote()), RepositoryImplementation(
        DataSourceLocal()
    ))
}
