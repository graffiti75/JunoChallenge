package br.cericatto.junochallenge.presenter

import android.app.SearchManager
import android.view.Menu
import android.view.MenuItem
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
    fun initRecyclerView()
    fun initDataSet(context: MainActivity, service : ApiService, query: String)
    fun showData(repos: List<Repo>)
    fun showErrorMessage(error: String)

    fun onCreateOptionsMenu(menu: Menu)
    fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView?
    fun searchViewListener(searchView: SearchView?, searchItem: MenuItem)
}