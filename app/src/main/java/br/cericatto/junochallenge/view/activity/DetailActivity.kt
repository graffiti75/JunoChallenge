package br.cericatto.junochallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.cericatto.junochallenge.MainApplication
import br.cericatto.junochallenge.R
import br.cericatto.junochallenge.presenter.NavigationUtils
import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.component.DaggerDetailComponent
import br.cericatto.junochallenge.presenter.di.module.DetailModule
import br.cericatto.junochallenge.presenter.impl.DetailPresenterImpl
import br.cericatto.junochallenge.view.activity.base.BaseActivity
import javax.inject.Inject

/**
 * DetailActivity.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 17, 2019
 */
class DetailActivity : BaseActivity() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Inject
    lateinit var mPresenter: DetailPresenterImpl

    @Inject
    lateinit var mApiService: ApiService

    //--------------------------------------------------
    // Base Activity
    //--------------------------------------------------

    override val contentView: Int
        get() = R.layout.activity_detail

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        setContentView(contentView)
        super.onViewReady(savedInstanceState, intent)
    }

    override fun resolveDaggerDependency() {
        DaggerDetailComponent.builder()
            .applicationComponent(applicationComponent)
            .detailModule(DetailModule(this))
            .build().inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavigationUtils.animate(this, NavigationUtils.Animation.BACK)
    }

    //--------------------------------------------------
    // Activity Lifecycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repoPositionInList = mPresenter.getExtras()
        val repo = MainApplication.repoList[repoPositionInList]
        setCustomToolbar(true, repo.name)
        mPresenter.showData(repo)
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}