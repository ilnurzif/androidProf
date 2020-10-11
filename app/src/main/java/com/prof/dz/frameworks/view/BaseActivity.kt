package com.prof.dz.frameworks.view

import androidx.appcompat.app.AppCompatActivity
import com.prof.dz.entities.DataModel
import com.prof.dz.interface_adapters.presenters.BaseViewModel
import com.prof.dz.use_case.interactors.IMainInteractor


abstract class BaseActivity<T : DataModel, I : IMainInteractor<T>> : AppCompatActivity() {
    protected  abstract val viewModel: BaseViewModel<T>
    abstract fun renderData(dataModel: DataModel)
}


