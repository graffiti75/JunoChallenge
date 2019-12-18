package br.cericatto.junochallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.component.DaggerMainComponent
import br.cericatto.junochallenge.presenter.di.module.MainModule
import br.cericatto.junochallenge.presenter.extensions.checkIfHasNetwork
import br.cericatto.junochallenge.presenter.extensions.setVisibilities
import br.cericatto.junochallenge.presenter.extensions.showToast
import br.cericatto.junochallenge.presenter.impl.MainPresenterImpl
import br.cericatto.junochallenge.view.activity.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * MainActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class MainActivity : BaseActivity() {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    companion object {
        const val LIST_SAVED_INSTANCE = "list_saved_instance"
    }

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
        getSavedInstanceState(savedInstanceState)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val json: String = Gson().toJson(MainApplication.repoList)
        outState.putString(LIST_SAVED_INSTANCE, json)
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

    private fun getSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val json = savedInstanceState.getString(LIST_SAVED_INSTANCE)
            val jsonType = object : TypeToken<MutableList<Repo>>(){}.type
            var repoList: MutableList<Repo> = Gson().fromJson(json, jsonType)

            if (repoList.isNotEmpty()) {
                setVisibilities(View.GONE, View.VISIBLE, View.GONE)
                MainApplication.repoList = repoList
                mPresenter.initRecyclerView()
                mPresenter.restoreDataSet(this, repoList)
            } else setVisibilities(View.GONE, View.GONE, View.VISIBLE)
        }
    }

    fun getData(query: String) {
        if (checkIfHasNetwork()) {
            mPresenter.initRecyclerView()
            mPresenter.initDataSet(this, mApiService, query)
        } else {
            setVisibilities(View.GONE, View.GONE, View.VISIBLE)
            showToast(R.string.no_internet)
        }
    }
}