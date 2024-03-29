package br.cericatto.junochallenge.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.junochallenge.AppConfiguration
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.presenter.extensions.getPage
import br.cericatto.junochallenge.presenter.extensions.getTotalCount
import br.cericatto.junochallenge.presenter.extensions.openActivityExtra
import br.cericatto.junochallenge.presenter.extensions.updatePage
import br.cericatto.junochallenge.presenter.impl.MainPresenterImpl
import br.cericatto.junochallenge.view.activity.DetailActivity
import br.cericatto.junochallenge.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * RepoAdapter.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
class RepoAdapter(activity: MainActivity, presenter : MainPresenterImpl)
    : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private val mActivity = activity
    private val mPresenter = presenter
    private var mRepoList: MutableList<String> = ArrayList()

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(inflater.inflate(R.layout.item_repo, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        var repo = mRepoList[position]
        var view = holder.itemView
        setTitle(view, repo, position)
        checkPagination(position)
    }

    override fun getItemCount(): Int = mRepoList.size

    //--------------------------------------------------
    // Callback
    //--------------------------------------------------

    fun updateAdapter(list: List<String>) {
        val newList = mRepoList
        newList.addAll(list)
        mRepoList = newList
        notifyDataSetChanged()
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun checkPagination(position: Int) {
        val callPagination = shouldCallPagination(position)
        if (callPagination) {
            mActivity.updatePage(mActivity.getPage() + 1)
            mPresenter.initDataSet()
        }
    }

    private fun shouldCallPagination(position: Int): Boolean {
        val shouldPaginate : Boolean = position == (mRepoList.size - 1)
        val page = mActivity.getPage()
        val totalCount = mActivity.getTotalCount()
        val reachedAllResults = totalCount < AppConfiguration.ITEMS_PER_PAGE * page
        return shouldPaginate && !reachedAllResults
    }

    private fun setTitle(view: View, repoName: String, position: Int) {
        view.id_item_repo__title_text_view.text = repoName
        view.id_item_repo__title_text_view.setOnClickListener {
            mActivity.openActivityExtra(mActivity, DetailActivity::class.java,
                AppConfiguration.REPO_ID_POSITION_EXTRA, position)
        }
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}