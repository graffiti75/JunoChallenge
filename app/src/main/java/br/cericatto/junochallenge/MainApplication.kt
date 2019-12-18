package br.cericatto.junochallenge

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import br.cericatto.junochallenge.model.Repo
import br.cericatto.junochallenge.presenter.di.component.ApplicationComponent
import br.cericatto.junochallenge.presenter.di.component.DaggerApplicationComponent
import br.cericatto.junochallenge.presenter.di.module.ApplicationModule
import br.cericatto.junochallenge.presenter.log.LineNumberDebugTree
import br.cericatto.junochallenge.presenter.log.ReleaseTree
import timber.log.Timber

/**
 * MainApplication.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
open class MainApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    companion object {
        lateinit var sharedPreferences: SharedPreferences

        var repoList : MutableList<Repo> = mutableListOf()
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initializeApplicationComponent()
        sharedPreferences = getSharedPreferences(AppConfiguration.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(LineNumberDebugTree())
        else Timber.plant(ReleaseTree())
    }

    private fun initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this, AppConfiguration.BASE_URL))
            .build()
    }
}