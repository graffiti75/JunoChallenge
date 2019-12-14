package br.cericatto.junochallenge.presenter.di.component

import android.content.Context
import br.cericatto.junochallenge.presenter.di.module.ApplicationModule
import dagger.Component
import retrofit2.Retrofit

/**
 * ApplicationComponent.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun exposeRetrofit(): Retrofit
    fun exposeContext(): Context
}