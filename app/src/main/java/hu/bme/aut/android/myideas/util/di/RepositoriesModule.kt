package hu.bme.aut.android.myideas.util.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.myideas.datasources.cacheDataSource.daos.IdeaDao
import hu.bme.aut.android.myideas.datasources.networkDataSource.NetworkDataSource
import hu.bme.aut.android.myideas.repositories.IdeaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideIdeaRepository(
        networkDataSource: NetworkDataSource,
        cacheDataSource: IdeaDao
    ): IdeaRepository {
        return IdeaRepository(
            networkDataSource = networkDataSource,
            cacheDataSource = cacheDataSource
        )
    }
}