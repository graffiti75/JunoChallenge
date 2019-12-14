package br.cericatto.junochallenge

import android.app.Application
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

    var page: Int = 1
    var loadedAllData: Boolean = false

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initializeApplicationComponent()
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