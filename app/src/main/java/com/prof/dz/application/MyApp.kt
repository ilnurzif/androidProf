package com.prof.dz.application

import android.app.Activity
import android.app.Application
import com.prof.dz.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : Application() , HasActivityInjector  {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}
