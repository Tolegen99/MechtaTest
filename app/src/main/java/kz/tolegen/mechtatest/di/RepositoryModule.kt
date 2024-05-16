package kz.tolegen.mechtatest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kz.tolegen.mechtatest.model.data.local.FavoriteDao
import kz.tolegen.mechtatest.model.data.remote.MechtaApi
import kz.tolegen.mechtatest.ui.feature.detail.DetailRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideDetailRepository(
        api: MechtaApi,
        favoriteDao: FavoriteDao,
    ): DetailRepository {
        return DetailRepository(api, favoriteDao)
    }
}