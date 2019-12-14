package br.cericatto.junochallenge.presenter.di.component

import br.cericatto.junochallenge.presenter.di.module.TestModule
import br.cericatto.junochallenge.presenter.di.scope.PerActivity
import br.cericatto.junochallenge.view.activity.MainActivity
import dagger.Component

/**
 * TestComponent.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@PerActivity
@Component(modules = [TestModule::class], dependencies = [ApplicationComponent::class])
interface TestComponent {
//    fun inject(target: TestActivity)
    fun inject(target: MainActivity)
}