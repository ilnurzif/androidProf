package com.prof.dz.frameworks.view

import android.os.Bundle
import com.prof.dz.R
import com.prof.dz.entities.DataModel
import com.prof.dz.interface_adapters.presenters.IPresenter
import com.prof.dz.interface_adapters.presenters.MainPresenterImpl
import com.prof.dz.use_case.interactors.MainInteractor

class MainActivity : BaseActivity<DataModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter(): IPresenter<DataModel, View> {
        val interactor: MainInteractor()
        return MainPresenterImpl(this)
    }

    override fun renderData(dataModel: DataModel) {
        TODO("Not yet implemented")
    }
}