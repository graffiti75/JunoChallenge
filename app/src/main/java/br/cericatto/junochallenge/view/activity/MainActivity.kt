package br.cericatto.junochallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.model.room.AppDatabase
import br.cericatto.junochallenge.model.room.RepoDao
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.component.DaggerMainComponent
import br.cericatto.junochallenge.presenter.di.module.MainModule
import br.cericatto.junochallenge.presenter.extensions.*
import br.cericatto.junochallenge.presenter.impl.MainPresenterImpl
import br.cericatto.junochallenge.view.activity.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
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
        const val LIST_POSITION_STATE = "list_position_state"
    }

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Inject
    lateinit var mPresenter: MainPresenterImpl

    @Inject
    lateinit var mApiService: ApiService

    private lateinit var mRepoDao: RepoDao
    var mListState: Parcelable? = null

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
        checkInstanceState(savedInstanceState)
        checkCachedData()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val json: String = Gson().toJson(MainApplication.repoList)
        outState.putString(LIST_SAVED_INSTANCE, json)
        outState.putParcelable(LIST_POSITION_STATE, id_activity_main__recycler_view.layoutManager?.onSaveInstanceState())
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mPresenter.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    //--------------------------------------------------
    // Public Methods
    //--------------------------------------------------

    fun getData(query: String) {
        if (checkIfHasNetwork()) {
            val totalCount = getTotalCount()
            val firstExecution = totalCount < 0
            val emptyResults = totalCount == 0
            if (emptyResults) setVisibilities(View.GONE, View.GONE, View.VISIBLE)
            else getNotEmptyData(firstExecution, query, totalCount)
        } else {
            updatePage(1)
            setVisibilities(View.GONE, View.GONE, View.VISIBLE)
            showToast(R.string.no_internet)
        }
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun checkInstanceState(savedInstanceState: Bundle?) {
        mListState = savedInstanceState?.getParcelable(LIST_POSITION_STATE)
        if (savedInstanceState != null) {
            val json = savedInstanceState.getString(LIST_SAVED_INSTANCE)
            val jsonType = object : TypeToken<MutableList<Repo>>(){}.type
            var repoList: MutableList<Repo> = Gson().fromJson(json, jsonType)

            if (repoList.isNotEmpty()) {
                getCachedRepoList(repoList)
            } else setVisibilities(View.GONE, View.GONE, View.VISIBLE)
        } else setVisibilities(View.GONE, View.GONE, View.VISIBLE)
    }

    private fun checkCachedData() {
        mRepoDao = AppDatabase.getInstance(this).repoDao()
        mRepoDao.getAll().observe(this, Observer<MutableList<Repo>> {
            if (it.isNotEmpty()) {
                getCachedRepoList(it)
            }
        })
    }

    private fun getCachedRepoList(repoList: MutableList<Repo>) {
        setVisibilities(View.GONE, View.VISIBLE, View.GONE)
        MainApplication.repoList = repoList
        mPresenter.initApiService(mApiService)
        mPresenter.initRecyclerView(mListState)
        mPresenter.restoreDataSet(this, repoList)
    }

    private fun getNotEmptyData(firstExecution: Boolean, query: String, totalCount: Int) {
        if (firstExecution) {
            mPresenter.initApiService(mApiService)
            mPresenter.initRecyclerView(mListState)
            mPresenter.initDataSet(this, mApiService, query)
            Timber.i("Query: $query")
        } else {
            val page = getPage()
            val getDataFromCloud = totalCount < AppConfiguration.ITEMS_PER_PAGE * page
            val reachedAllResults = firstExecution || getDataFromCloud
            if (!reachedAllResults) {
                mPresenter.initApiService(mApiService)
                mPresenter.initRecyclerView(mListState)
                mPresenter.initDataSet(this, mApiService, query)
                Timber.i("Query: $query")
            }
        }
    }
}