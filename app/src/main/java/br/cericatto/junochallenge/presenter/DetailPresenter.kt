package br.cericatto.junochallenge.presenter

import br.cericatto.junochallenge.presenter.api.ApiService

/**
 * DetailPresenter.kt.
 * 
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
interface DetailPresenter {
    fun getExtras(): String
    fun initDataSet(service : ApiService, userLogin: String, password: String, repoName: String)
    fun showData()
    fun showErrorMessage(error: String)
}