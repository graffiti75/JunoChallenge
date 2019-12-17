package br.cericatto.junochallenge.presenter.impl

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Search
import br.cericatto.junochallenge.presenter.MainPresenter
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.extensions.showToast
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

    private lateinit var mAdapter: RepoAdapter
    private lateinit var mService: ApiService
    private lateinit var mRepos: List<String>
    private lateinit var mQuery: String

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    override fun initRecyclerView() {
        mAdapter = RepoAdapter(mActivity, this)
        mActivity.id_activity_main__recycler_view.adapter = mAdapter
        mActivity.id_activity_main__recycler_view.layoutManager = LinearLayoutManager(mActivity)
    }

    override fun initDataSet(context: MainActivity, service: ApiService, query: String) {
        val app: MainApplication = context.application as MainApplication
        val page = app.page
        if (page == 1) {
            mService = service
        }

        val observable = service.getRepos(query = query)
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    loadDataOnSuccess(it, app)
                },
                {
                    it.message?.let { errorMessage ->
                        showErrorMessage(errorMessage)
                        context.showToast(context.getString(R.string.retrofit_error))
                    }
                },
                // OnCompleted
                {
                    Timber.i("getRepos() -> At OnCompleted.")
                }
            )
        val composite = CompositeDisposable()
        composite.add(subscription)
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
        var searchView = setSearchView(searchItem, searchManager)
        searchViewListener(searchView, searchItem)
    }

    override fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView? {
        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem!!.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(mActivity.componentName))
        }
        return searchView
    }

    override fun searchViewListener(searchView: SearchView?, searchItem: MenuItem) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mActivity.id_activity_main__loading.visibility = View.VISIBLE
                mActivity.id_activity_main__default_text.visibility = View.GONE

                if (!searchView?.isIconified) {
                    searchView?.isIconified = true
                }
                searchItem.collapseActionView()

                mQuery = query
                mActivity.getData(query)
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
        initDataSet(mActivity, mService, mQuery)
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun loadDataOnSuccess(search: Search?, app: MainApplication) {
        val items = search?.items ?: emptyList()
        MainApplication.repoList.addAll(items)
        val itemsName : MutableList<String> = mutableListOf()
        items.forEach {
            itemsName.add(it.name)
        }
        if (items.isNotEmpty()) {
            showData(itemsName)
            Timber.i("getRepos() -> $items")
        } else {
            app.loadedAllData = true
        }
    }

    private fun updateAdapter(repos: List<String>) {
        mActivity.id_activity_main__loading.visibility = View.GONE
        mActivity.id_activity_main__recycler_view.visibility = View.VISIBLE

        mRepos = repos
        mAdapter.updateAdapter(repos)
    }
}