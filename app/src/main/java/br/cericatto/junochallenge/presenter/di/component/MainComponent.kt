package br.cericatto.junochallenge.presenter.di.component

import br.cericatto.junochallenge.presenter.di.module.MainModule
import br.cericatto.junochallenge.presenter.di.scope.PerActivity
import br.cericatto.junochallenge.view.activity.MainActivity
import dagger.Component

/**
 * MainComponent.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@PerActivity
@Component(modules = [MainModule::class], dependencies = [ApplicationComponent::class])
interface MainComponent {
    fun inject(target: MainActivity)
}