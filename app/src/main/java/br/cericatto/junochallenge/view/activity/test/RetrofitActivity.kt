package br.cericatto.junochallenge.view.activity.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.api.OkHttpProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * TestActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 15, 2019
 */
class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfiguration.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpProvider.instance)
            .build()

        val service = retrofit.create(ApiService::class.java)
        val observable = service.getRepos()
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    github_repo_forks.text = it.items[0].forks.toString()
                },
                {
                    github_repo_forks.text = it.message
                },
                // OnCompleted
                {}
            )
        val composite = CompositeDisposable()
        composite.add(subscription)
    }
}