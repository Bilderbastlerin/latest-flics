package de.franziskaneumeister.recentflics.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.movito.themoviedbapi.TmdbApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public abstract class DataModule {

    public companion object {

        @Provides
        @Singleton
        internal fun providesTmdbApi(): TmdbApi {
            return TmdbApi(
                "some api key"
            )

        }
    }
}