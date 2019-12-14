package br.cericatto.junochallenge.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.impl.MainPresenterImpl
import br.cericatto.junochallenge.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_repo.view.*
import timber.log.Timber

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
    private var mRepoList: MutableList<Repo> = ArrayList()

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
        setTitle(view, repo)
        checkPagination(position)
    }

    override fun getItemCount(): Int = mRepoList.size

    //--------------------------------------------------
    // Callback
    //--------------------------------------------------

    fun updateAdapter(list: List<Repo>) {
        val newList = mRepoList
        newList.addAll(list)
        mRepoList = newList
        notifyDataSetChanged()
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun checkPagination(position: Int) {
        val shouldPaginate : Boolean = position == (mRepoList.size - 1)
        Timber.d("position: $position, itemCount - 1: ${mRepoList.size - 1}, shouldPaginate: $shouldPaginate")

        val app: MainApplication = mActivity.application as MainApplication
        val loadedAllData = app.loadedAllData
        val page = app.page
        if (!loadedAllData && shouldPaginate) {
            app.page = page + 1
            Timber.d("page: $page")
            mPresenter.initDataSet()
        }
    }

    private fun setTitle(view: View, repo: Repo) {
        view.id_item_repo__title_text_view.text = repo.name
        view.id_item_repo__title_text_view.setOnClickListener {
//            mActivity.openActivityExtra(mActivity, DetailActivity::class.java,
//                AppConfiguration.REPO_NAME_EXTRA, repo.name)
        }
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}