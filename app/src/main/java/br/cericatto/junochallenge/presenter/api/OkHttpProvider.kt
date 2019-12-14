package br.cericatto.junochallenge.presenter.api

import okhttp3.OkHttpClient

/**
 * OkHttpProvider.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
object OkHttpProvider {
    val instance: OkHttpClient = OkHttpClient.Builder().build()
}