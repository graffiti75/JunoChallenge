package br.cericatto.junochallenge.presenter.impl

import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.DetailPresenter
import br.cericatto.junochallenge.view.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import javax.inject.Inject

/**
 * DetailPresenterImpl.
 *
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
class DetailPresenterImpl @Inject constructor(private val mActivity: DetailActivity): DetailPresenter {

    override fun getExtras(): Int {
        val extras = mActivity.intent.extras
        if (extras != null) return extras.getInt(AppConfiguration.REPO_ID_POSITION_EXTRA)
        return 0
    }

    override fun showData(repo: Repo) {
        with(repo) {
            mActivity.id_activity_details__id_text_view.text = this.id
            mActivity.id_activity_details__node_id_text_view.text = this.node_id
            mActivity.id_activity_details__name_text_view.text = this.name
            mActivity.id_activity_details__full_name_text_view.text = this.full_name
            mActivity.id_activity_details__private_text_view.text = this.private.toString()
            mActivity.id_activity_details__owner_id_text_view.text = this.owner.id
            mActivity.id_activity_details__owner_login_text_view.text = this.owner.login
            mActivity.id_activity_details__stargazers_count_text_view.text = this.stargazers_count.toString()
            mActivity.id_activity_details__watchers_count_text_view.text = this.watchers_count.toString()
            mActivity.id_activity_details__forks_text_view.text = this.forks.toString()
            mActivity.id_activity_details__open_issues_text_view.text = this.open_issues.toString()
            mActivity.id_activity_details__score_text_view.text = this.score.toString()
        }
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }
}
