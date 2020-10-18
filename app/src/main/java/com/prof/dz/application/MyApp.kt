package com.prof.dz.application

import android.app.Application
import androidx.room.Room
import com.prof.dz.application.MyApp.companion.myApp
import com.prof.dz.frameworks.db.RoomDataBaseImplementation
import com.prof.dz.frameworks.db.room.HistoryDataBase
import com.prof.dz.frameworks.koin.application
import com.prof.dz.frameworks.koin.mainViewModel_
import com.prof.dz.use_case.repositories.RepositoryImplementationLocal
import org.koin.core.context.startKoin

class MyApp : Application()  {

   lateinit var roomRepo : RepositoryImplementationLocal
    override fun onCreate() {
        super.onCreate()
  /*      startKoin {
            modules(listOf(application, mainViewModel_))
        }*/

        roomRepo= RepositoryImplementationLocal(RoomDataBaseImplementation(Room.databaseBuilder(this, HistoryDataBase::class.java, "HistoryDB2").build().historyDao()))
        myApp=this
    }

    open fun getApp():MyApp {
        return myApp
    }

    object companion {
        open lateinit var myApp: MyApp
    }
}
