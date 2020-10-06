package com.prof.dz.interface_adapters.presenters


import com.prof.dz.entities.DataModel
import com.prof.dz.frameworks.view.View
import com.prof.dz.use_case.interactors.IMainInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : DataModel, V : View>(
/*    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),*/
    private val interactor: IMainInteractor<T>,
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
 //   protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : IPresenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

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
        { currentView?.renderData(DataModel.Loading(null)) }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {

            override fun onNext(data: DataModel) {
                currentView?.renderData(data)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(DataModel.Error(e))
            }

            override fun onComplete() {
            }
        }
    }

}
