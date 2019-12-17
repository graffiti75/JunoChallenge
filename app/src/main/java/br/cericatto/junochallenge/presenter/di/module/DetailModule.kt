package br.cericatto.junochallenge.presenter.di.module

import br.cericatto.junochallenge.presenter.api.ApiService
import br.cericatto.junochallenge.presenter.di.scope.PerActivity
import br.cericatto.junochallenge.view.activity.DetailActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * DetailModule.kt.
 *
 * @author Rodrigo Cericatto
 * @since December 14, 2019
 */
@Module
class DetailModule(private val mActivity: DetailActivity) {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @PerActivity
    @Provides
    fun provideActivity(): DetailActivity {
        return mActivity
    }
}