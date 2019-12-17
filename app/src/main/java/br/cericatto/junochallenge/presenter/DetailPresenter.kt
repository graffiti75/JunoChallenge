package br.cericatto.junochallenge.presenter

import br.cericatto.junochallenge.model.Repo

/**
 * DetailPresenter.kt.
 * 
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
interface DetailPresenter {
    fun getExtras(): Int
    fun showData(repo: Repo)
    fun showErrorMessage(error: String)
}