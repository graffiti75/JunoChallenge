package br.cericatto.junochallenge.presenter

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
    fun initDataSet(context: MainActivity, service : ApiService)
    fun showData(repos: List<Repo>)
    fun showErrorMessage(error: String)
}