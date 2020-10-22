package com.prof.dz.application

import android.app.Application
import com.prof.dz.frameworks.koin.application
import com.prof.dz.frameworks.koin.historyViewModel_
import com.prof.dz.frameworks.koin.mainViewModel_
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : Application()  {

    override fun onCreate() {
        super.onCreate()

  startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf (application, mainViewModel_,historyViewModel_))
        }

    }
}
