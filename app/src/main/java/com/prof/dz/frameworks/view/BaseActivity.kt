package com.prof.dz.frameworks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prof.dz.entities.DataModel
import com.prof.dz.interface_adapters.presenters.IPresenter

abstract class BaseActivity<T : DataModel> : AppCompatActivity(), View {

    protected lateinit var presenter: IPresenter<T, View>
    protected abstract fun createPresenter(): IPresenter<T, View>
    abstract override fun renderData(dataModel: DataModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}
