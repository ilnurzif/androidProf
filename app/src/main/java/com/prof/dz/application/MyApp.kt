package com.prof.dz.application

import android.app.Application
import com.prof.dz.frameworks.koin.application
import com.prof.dz.frameworks.koin.historyViewModel_
import com.prof.dz.frameworks.koin.mainViewModel_
import org.koin.android.ext.android.startKoin


class MyApp : Application()  {
    override fun onCreate() {
        super.onCreate()
        val moduleList = listOf (application, mainViewModel_,historyViewModel_)
         startKoin (this, moduleList)
    }
}
