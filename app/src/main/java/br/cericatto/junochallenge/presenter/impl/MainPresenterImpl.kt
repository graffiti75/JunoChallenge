package br.cericatto.junochallenge.presenter.impl

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.MainPresenter
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.extensions.showToast
import br.cericatto.junochallenge.view.activity.MainActivity
import br.cericatto.junochallenge.view.adapter.RepoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

/**
 * MainPresenterImpl.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class MainPresenterImpl @Inject constructor(private val mActivity: MainActivity): MainPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private lateinit var mAdapter: RepoAdapter
    private lateinit var mService : ApiService

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    override fun initRecyclerView() {
        mAdapter = RepoAdapter(mActivity, this)
        mActivity.id_activity_main__recycler_view.adapter = mAdapter
        mActivity.id_activity_main__recycler_view.layoutManager = LinearLayoutManager(mActivity)
    }

    override fun initDataSet(context: MainActivity, service : ApiService) {
        val app: MainApplication = context.application as MainApplication
        val page = app.page
        if (page == 1) {
            mService = service
        }

        val observable = service.getRepos()
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val items = it?.items ?: emptyList()
                    if (items.isNotEmpty()) {
                        showData(items)
                        Timber.i("getRepos() -> $items")
                    } else {
                        app.loadedAllData = true
                    }
                },
                {
                    it.message?.let { errorMessage ->
                        showErrorMessage(errorMessage)
                        context.showToast(context.getString(R.string.activity_login__authentication_error))
                        backToLoginScreen(context)
                    }
                },
                // OnCompleted
                {
                    Timber.i("getRepos() -> At OnCompleted.")
                }
            )
        val composite = CompositeDisposable()
        composite.add(subscription)
    }

    override fun showData(repos: List<Repo>) {
        updateAdapter(repos)
        Timber.d(repos.toString())
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }

    //--------------------------------------------------
    // Public Methods
    //--------------------------------------------------

    fun initDataSet() {
        initDataSet(mActivity, mService)
    }

    fun callLoading() {
        mActivity.id_activity_main__loading.visibility = View.VISIBLE
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun updateAdapter(repos: List<Repo>) {
        mActivity.id_activity_main__loading.visibility = View.GONE
        mActivity.id_activity_main__recycler_view.visibility = View.VISIBLE

        mAdapter.updateAdapter(repos)
    }

    private fun backToLoginScreen(context: MainActivity) {
//        context.openActivity(context, LoginActivity::class.java)
    }
}