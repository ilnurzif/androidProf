package com.prof.dz.di

import android.app.Application
import com.prof.dz.application.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

 @Component(
     modules = [
       InteractorModule::class,
       ViewModelModule::class,
       ActivityModule::class,
       AndroidSupportInjectionModule::class]
 )
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: MyApp)
}
