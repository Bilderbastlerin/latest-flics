package de.franziskaneumeister.recentflics.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.franziskaneumeister.recentflics.core.database.AppDatabase
import de.franziskaneumeister.recentflics.core.database.MovieDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class DatabaseModule {

    @Provides
    internal fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        )
            .build()
    }
}