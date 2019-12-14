package br.cericatto.junochallenge.presenter.di.module

import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.scope.PerActivity
import br.cericatto.junochallenge.view.activity.MainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * MainModule.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@Module
class MainModule(private val mActivity: MainActivity) {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @PerActivity
    @Provides
    fun provideActivity(): MainActivity {
        return mActivity
    }
}