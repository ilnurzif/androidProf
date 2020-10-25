package com.less.historyscreen.frameworks.koin

import com.less.historyscreen.use_case.HistoryInteractor
import com.less.historyscreen.viwmodels.HistoryViewModel
import com.less.repository.db.RoomDataBaseImplementation
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyViewModel_))
}


val historyViewModel_ = module {
    factory<HistoryInteractor> {
        HistoryInteractor(
            get<RoomDataBaseImplementation>()
        )
    }
    factory { HistoryViewModel(get()) }
}