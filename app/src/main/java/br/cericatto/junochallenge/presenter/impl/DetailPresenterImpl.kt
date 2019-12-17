package br.cericatto.junochallenge.presenter.impl

import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.DetailPresenter
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.view.activity.DetailActivity
import br.cericatto.junochallenge.view.adapter.RepoDetailsAdapter
import timber.log.Timber
import javax.inject.Inject

/**
 * DetailPresenterImpl.
 *
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
class DetailPresenterImpl @Inject constructor(private val mActivity: DetailActivity): DetailPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private lateinit var mAdapter: RepoDetailsAdapter
    private var mData : MutableList<Repo> = mutableListOf()

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    override fun getExtras(): String {
//        val extras = mActivity.intent.extras
//        if (extras != null) return extras.getString(AppConfiguration.REPO_NAME_EXTRA)
        return ""
    }

    override fun initDataSet(service : ApiService, userLogin: String, password: String, repoName: String) {
        /*
        val observable = service.getCommits(
            userLogin.getHeaderAuthentication(password), userLogin, repoName)
        val subscription = observable
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable { it.take(AppConfiguration.COMMITS_NUMBER) }
            .subscribe(
                {
                    concatData(it)
                    Timber.i("getCommits() -> $it")
                },
                {
                    it.message?.let { errorMessage ->
                        showErrorMessage(errorMessage)
                        mActivity.showToast(mActivity.getString(R.string.activity_detail__authentication_error))
                        mActivity.finish()
                    }
                },
                // OnCompleted
                {
                    showData()
                }
            )
        val composite = CompositeDisposable()
        composite.add(subscription)
        */
    }

    override fun showData() {
        if (mData.isEmpty()) setEmptyTextView()
        else setAdapter(mData)
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun concatData(data: Repo) {
        mData.add(data)
    }

    private fun setAdapter(commits: List<Repo>) {
//        mActivity.id_activity_detail__loading.visibility = View.GONE
//        mActivity.id_activity_detail__recycler_view.visibility = View.VISIBLE
//        mActivity.id_activity_detail__empty_text_view.visibility = View.GONE

//        mAdapter = CommitAdapter(mActivity, commits)
//        mActivity.id_activity_detail__recycler_view.adapter = mAdapter
//        mActivity.id_activity_detail__recycler_view.layoutManager = LinearLayoutManager(mActivity)
    }

    private fun setEmptyTextView() {
//        mActivity.id_activity_detail__loading.visibility = View.GONE
//        mActivity.id_activity_detail__recycler_view.visibility = View.GONE
//        mActivity.id_activity_detail__empty_text_view.visibility = View.VISIBLE
    }
}
