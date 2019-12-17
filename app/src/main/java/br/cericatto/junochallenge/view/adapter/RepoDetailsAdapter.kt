package br.cericatto.junochallenge.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.view.activity.DetailActivity
import kotlinx.android.synthetic.main.item_repo_details.view.*

/**
 * CommitAdapter.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
class RepoDetailsAdapter(activity: DetailActivity, list: List<Repo>) : RecyclerView.Adapter<RepoDetailsAdapter.CommitViewHolder>() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private var mCommitList = list

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommitViewHolder(inflater.inflate(R.layout.item_repo_details, parent, false))
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        var commit = mCommitList[position]
        var view = holder.itemView
        setTexts(view, commit)
    }

    override fun getItemCount(): Int = mCommitList.size

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun setTexts(view: View, repo: Repo) {
        view.id_item_repo__id_text_view.text = repo.id
        view.id_item_repo__node_id_text_view.text = repo.node_id
        view.id_item_repo__name_text_view.text = repo.name
        view.id_item_repo__full_name_text_view.text = repo.full_name
        view.id_item_repo__private_text_view.text = repo.private.toString()
        view.id_item_repo__owner_id_text_view.text = repo.owner.id
        view.id_item_repo__owner_login_text_view.text = repo.owner.login
        view.id_item_repo__stargazers_count_text_view.text = repo.stargazers_count.toString()
        view.id_item_repo__watchers_count_text_view.text = repo.watchers_count.toString()
        view.id_item_repo__forks_text_view.text = repo.forks.toString()
        view.id_item_repo__open_issues_text_view.text = repo.open_issues.toString()
        view.id_item_repo__score_text_view.text = repo.score.toString()
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}