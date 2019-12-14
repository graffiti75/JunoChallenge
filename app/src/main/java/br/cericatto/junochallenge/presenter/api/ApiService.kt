package br.cericatto.junochallenge.presenter.api

import br.cericatto.junochallenge.model.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * ApiService.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
interface ApiService {
    @GET("/user/repos")
    fun getRepos(@Header("Authorization") authorization: String,
        @Query("page") page: Int): Observable<MutableList<Repo>>

    /*
    @GET("/repos/{user_login}/{repo_name}/commits")
    fun getCommits(@Header("Authorization") authorization: String,
        @Path("user_login") userLogin: String,
        @Path("repo_name") repoName: String): Observable<List<GithubCommit>>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>
     */
}