package br.cericatto.junochallenge.presenter.di.component

import br.cericatto.junochallenge.presenter.di.module.DetailModule
import br.cericatto.junochallenge.presenter.di.scope.PerActivity
import br.cericatto.junochallenge.view.activity.DetailActivity
import dagger.Component

/**
 * DetailComponent.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@PerActivity
@Component(modules = [DetailModule::class], dependencies = [ApplicationComponent::class])
interface DetailComponent {
    fun inject(target: DetailActivity)
}