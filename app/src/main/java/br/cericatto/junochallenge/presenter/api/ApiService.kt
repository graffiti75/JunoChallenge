package br.cericatto.junochallenge.presenter.api

import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.model.Search
import br.cericatto.junochallenge.model.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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
                 @Query("per_page") perPage: Int = AppConfiguration.ITEMS_PER_PAGE,
                 @Query("page") page: Int = AppConfiguration.FIRST_PAGE)
    : Observable<Search>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>
}