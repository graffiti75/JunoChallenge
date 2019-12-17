package br.cericatto.junochallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.component.DaggerMainComponent
import br.cericatto.junochallenge.presenter.di.extensions.checkIfHasNetwork
import br.cericatto.junochallenge.presenter.di.extensions.showToast
import br.cericatto.junochallenge.presenter.di.module.MainModule
import br.cericatto.junochallenge.presenter.impl.MainPresenterImpl
import br.cericatto.junochallenge.view.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * MainActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class MainActivity : BaseActivity() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Inject
    lateinit var mPresenter: MainPresenterImpl

    @Inject
    lateinit var mApiService: ApiService

    //--------------------------------------------------
    // Base Activity
    //--------------------------------------------------

    override val contentView: Int
        get() = R.layout.activity_main

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        setContentView(contentView)
        super.onViewReady(savedInstanceState, intent)
    }

    @Suppress("DEPRECATION")
    override fun resolveDaggerDependency() {
        DaggerMainComponent.builder()
            .applicationComponent(applicationComponent)
            .mainModule(MainModule(this))
            .build().inject(this)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    //--------------------------------------------------
    // Activity Lifecycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomToolbar(false, getString(R.string.activity_main))
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mPresenter.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    fun getData(query: String) {
        if (checkIfHasNetwork()) {
            mPresenter.initRecyclerView()
            mPresenter.initDataSet(this, mApiService, query)
        } else {
            id_activity_main__loading.visibility = View.GONE
            id_activity_main__recycler_view.visibility = View.GONE
            id_activity_main__default_text.visibility = View.VISIBLE

            showToast(R.string.no_internet)
        }
    }
}