package com.prof.dz.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApp : Application()  {

    override fun onCreate() {
        super.onCreate()

 /* startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf (application, mainViewModel_,historyViewModel_))
        }*/

        startKoin { androidContext(this@MyApp) }

    }
}
