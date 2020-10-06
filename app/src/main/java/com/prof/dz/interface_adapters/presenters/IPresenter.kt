package com.prof.dz.interface_adapters.presenters

import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.view.View
import io.reactivex.Scheduler

interface IPresenter<T : DataModel, V: View> {
    fun attachView(view:V)
    fun detachView(view:V)
    fun getData(word: String, isOnline: Boolean, ioSheduler: Scheduler, uiSheduler: Scheduler)
}