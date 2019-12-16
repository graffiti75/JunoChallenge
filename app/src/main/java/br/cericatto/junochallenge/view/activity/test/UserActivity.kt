package br.cericatto.junochallenge.view.activity.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.User
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.api.OkHttpProvider
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * UserActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 15, 2019
 */
class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfiguration.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpProvider.instance)
            .build()

        val githubService = retrofit.create(ApiService::class.java)
        githubService.getUser(AppConfiguration.TEST_GITHUB_USER).enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                github_user_name.text = getString(R.string.error_loading_user)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    github_user_name.text = response.body()?.login
                } else {
                    github_user_name.text = getString(R.string.error_loading_user)
                }
            }
        })
    }
}
