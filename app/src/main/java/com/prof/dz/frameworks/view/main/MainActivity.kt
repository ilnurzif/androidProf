package com.prof.dz.frameworks.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.less.core.BaseActivity
import com.less.model.DataModel
import com.less.model.SearchResult
import com.less.utills.viewById
import com.prof.dz.R
import com.prof.dz.frameworks.koin.injectDependencies
import com.prof.dz.frameworks.view.WordDescrActivity
import com.prof.dz.interface_adapters.viewmodels.MainViewModel
import com.prof.dz.use_case.interactors.MainInteractor
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope


class MainActivity() : BaseActivity<DataModel, MainInteractor>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =   "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        const val HISTORY_ACTIVITY_PATH = "com.less.historyscreen.frameworks.view.HistoryActivity"
        const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
    }

    private val REQUEST_CODE = 234
    private val stateUpdatedListener: InstallStateUpdatedListener = object : InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState?) {
            state?.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
         Snackbar.make(
            findViewById(R.id.main_activity),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onResume() {
        super.onResume()
        checkForUpdates()
    }

    private var adapter: MainAdapter? = null
   override lateinit var viewModel: MainViewModel
  //  override val viewModel : MainViewModel by currentScope.inject()
    private lateinit var splitInstallManager: SplitInstallManager

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo

        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }


    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.less.model.SearchResult) {
                startActivity(
                    WordDescrActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        data.meanings?.get(0)?.translation?.translation!!,
                        data.meanings?.get(0)?.imageUrl
                    ))
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                historyScreenCreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return true
    }

    fun historyScreenCreate() {
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        val request =
            SplitInstallRequest
                .newBuilder()
                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    fun search(isOnline:Boolean) {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object :
            SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getData(searchWord, isOnline)
            }
        })
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val search_fab by viewById<FloatingActionButton>(R.id.search_fab)
        search_fab.setOnClickListener {
            search(true)
        }

        injectDependencies()
   //    val tempViewModel: MainViewModel by viewModel()
     //   viewModel = tempViewModel
       val tempViewModel : MainViewModel by currentScope.inject()
       viewModel = tempViewModel
       viewModel.subscribe().observe(this@MainActivity, Observer<DataModel> { renderData(it) })
    }


    override fun showErrorScreen(error: String?) {
        showViewError()
        val error_textview by viewById<TextView>(R.id.error_textview)
        error_textview.text = error ?: getString(R.string.undefined_error)
        val reload_button by viewById<FloatingActionButton>(R.id.reload_button)
        reload_button.setOnClickListener {
        }
    }

    override fun showViewSuccess() {
        success_linear_layout.visibility = android.view.View.VISIBLE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.GONE
    }

     override fun showViewLoading() {
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.VISIBLE
        error_linear_layout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = android.view.View.GONE
        loading_frame_layout.visibility = android.view.View.GONE
        error_linear_layout.visibility = android.view.View.VISIBLE
    }


    override fun setDataToAdapter(searchResult: List<SearchResult>) {
         showViewSuccess()
        if (adapter == null) {
            main_activity_recyclerview.layoutManager =
                LinearLayoutManager(applicationContext)
            main_activity_recyclerview.adapter =
                MainAdapter(onListItemClickListener, searchResult)
        } else {
            adapter!!.setData(searchResult)
        }
    }

    override fun progeressVisualization(dataModel: DataModel.Loading) {
        if (dataModel.progress != null) {
            progress_bar_horizontal.visibility = android.view.View.VISIBLE
            progress_bar_round.visibility = android.view.View.GONE
            progress_bar_horizontal.progress = dataModel.progress!!
        } else {
            progress_bar_horizontal.visibility = android.view.View.GONE
            progress_bar_round.visibility = android.view.View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}