package com.prof.dz.interface_adapters.presenters

import com.prof.dz.entities.DataModel
import com.prof.dz.use_case.interactors.MainInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor): BaseViewModel<DataModel>() {

    override fun getData(word: String, isOnline: Boolean, ioSheduler: Scheduler, uiSheduler: Scheduler) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(ioSheduler)
                .observeOn(uiSheduler)
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { msgLiveData.value = DataModel.Loading(null) }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {

            override fun onNext(data: DataModel) {
                msgLiveData.value=data
            }

            override fun onError(e: Throwable) {
                msgLiveData.value = DataModel.Error(e)
            }

            override fun onComplete() {
            }
        }
    }

}