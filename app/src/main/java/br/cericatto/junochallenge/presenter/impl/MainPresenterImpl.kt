package br.cericatto.junochallenge.presenter.impl

import android.app.SearchManager
import android.content.Context
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.model.Search
import br.cericatto.junochallenge.model.room.AppDatabase
import br.cericatto.junochallenge.model.room.AppExecutors
import br.cericatto.junochallenge.model.room.RepoDao
import br.cericatto.junochallenge.presenter.MainPresenter
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.extensions.*
import br.cericatto.junochallenge.view.activity.MainActivity
import br.cericatto.junochallenge.view.adapter.RepoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

/**
 * MainPresenterImpl.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class MainPresenterImpl @Inject constructor(private val mActivity: MainActivity): MainPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    companion object {
        lateinit var mService: ApiService
        lateinit var mQuery: String
    }

    private lateinit var mAdapter: RepoAdapter
    private lateinit var mRepos: List<String>

    private lateinit var mRepoDao: RepoDao

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    override fun initApiService(service: ApiService) {
        mService = service
    }

    override fun initRecyclerView(state: Parcelable?) {
        mAdapter = RepoAdapter(mActivity, this)
        mActivity.id_activity_main__recycler_view.adapter = mAdapter
        var layoutManager = LinearLayoutManager(mActivity)
        mActivity.id_activity_main__recycler_view.layoutManager = layoutManager
        layoutManager.onRestoreInstanceState(state)
    }

    override fun initDataSet(context: MainActivity, service: ApiService, query: String) {
        val observable = service.getRepos(page = context.getPage(), query = query)
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    loadDataOnSuccess(it, query)
                },
                {
                    loadDataOnError(it, context)
                },
                { Timber.i("getRepos() -> At OnCompleted.") })
        val composite = CompositeDisposable()
        composite.add(subscription)
    }

    override fun restoreDataSet(context: MainActivity, repoList: MutableList<Repo>) {
        val itemsName = getRepoFullNameList(repoList)
        showData(itemsName)
    }

    override fun showData(repos: List<String>) {
        updateAdapter(repos)
        Timber.d(repos.toString())
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }

    override fun onCreateOptionsMenu(menu: Menu) {
        val menuInflater = mActivity.menuInflater
        menuInflater.inflate(R.menu.dashboard, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = mActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = setSearchView(searchItem, searchManager)
        searchViewListener(searchView, searchItem)
    }

    override fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView? {
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.componentName))
        return searchView
    }

    override fun searchViewListener(searchView: SearchView?, searchItem: MenuItem) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mActivity.updateCachedQuery(query)
                deleteFromDatabase()
                onQueryTextSubmitAction(searchView, searchItem, query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }


    //--------------------------------------------------
    // Public Methods
    //--------------------------------------------------

    fun initDataSet() {
        initDataSet(mActivity, mService, mActivity.getCachedQuery())
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun onQueryTextSubmitAction(searchView: SearchView?, searchItem: MenuItem, query: String) {
        mActivity.setVisibilities(View.VISIBLE, View.GONE, View.GONE)
        if (!searchView?.isIconified!!) {
            searchView.isIconified = true
        }
        searchItem.collapseActionView()
        mQuery = query
        mActivity.updatePage(1)
        mActivity.updateTotalCount(-1)
        mActivity.updateCachedQuery(query)
        mActivity.getData(query)
    }

    private fun loadDataOnSuccess(search: Search?, query: String) {
        val count = search?.total_count!!.toInt()
        mActivity.updateTotalCount(count)

        val repoList = search.items
        if (repoList.isNotEmpty()) {
            saveInDatabase(repoList)
            dataIsNotEmpty(repoList)
        } else {
            deleteFromDatabase()
            mActivity.updatePage(1)
            dataIsEmpty(query)
        }
    }

    private fun saveInDatabase(repoList: MutableList<Repo>) {
        mRepoDao = AppDatabase.getInstance(mActivity).repoDao()
        AppExecutors.instance?.diskIO()?.execute {
            mRepoDao.insertAll(repoList)
        }
    }

    private fun deleteFromDatabase() {
        mRepoDao = AppDatabase.getInstance(mActivity).repoDao()
        AppExecutors.instance?.diskIO()?.execute {
            mRepoDao.deleteAll()
        }
    }

    private fun loadDataOnError(error: Throwable, context: MainActivity) {
        error.message?.let { errorMessage ->
            showErrorMessage(errorMessage)
            context.showToast(context.getString(R.string.retrofit_error))
        }
    }

    private fun dataIsEmpty(query: String) {
        mActivity.setVisibilities(View.GONE, View.GONE, View.VISIBLE)
        val text = mActivity.getString(R.string.retrofit_empty_repos, query)
        mActivity.id_activity_main__default_text.text = text
    }

    private fun dataIsNotEmpty(repoList: List<Repo>) {
        MainApplication.repoList.addAll(repoList)
        val itemsName = getRepoFullNameList(repoList)
        showData(itemsName)
        Timber.i("Repos: $repoList")
    }

    private fun getRepoFullNameList(repoList: List<Repo>): MutableList<String> {
        val itemsName : MutableList<String> = mutableListOf()
        repoList.forEach {
            itemsName.add(it.full_name)
        }
        return itemsName
    }

    private fun updateAdapter(repos: List<String>) {
        mActivity.setVisibilities(View.GONE, View.VISIBLE, View.GONE)
        mRepos = repos
        mAdapter.updateAdapter(repos)
    }
}