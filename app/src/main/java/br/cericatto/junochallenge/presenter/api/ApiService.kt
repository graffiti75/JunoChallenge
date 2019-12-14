package br.cericatto.junochallenge.presenter.api

import br.cericatto.junochallenge.model.Search
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiService.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
interface ApiService {
    @GET("/search/repositories")
    fun getRepos(@Query("q") query: String = "android",
         @Query("per_page") perPage: Int = 30,
         @Query("page") page: Int = 1)
    : Observable<Search>
}