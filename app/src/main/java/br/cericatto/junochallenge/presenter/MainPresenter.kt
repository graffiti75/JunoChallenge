package br.cericatto.junochallenge.presenter

import android.app.SearchManager
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.view.activity.MainActivity

/**
 * MainPresenter.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
interface MainPresenter {
    fun initApiService(service: ApiService)
    fun initRecyclerView(state: Parcelable?)

    fun initDataSet(context: MainActivity, service : ApiService, query: String)
    fun restoreDataSet(context: MainActivity, repoList: MutableList<Repo>)
    fun showData(repos: List<String>)
    fun showErrorMessage(error: String)

    fun onCreateOptionsMenu(menu: Menu)
    fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView?
    fun searchViewListener(searchView: SearchView?, searchItem: MenuItem)
}