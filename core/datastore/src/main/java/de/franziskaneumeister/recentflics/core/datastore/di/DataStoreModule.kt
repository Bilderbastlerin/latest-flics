@file:OptIn(InternalSerializationApi::class)

package de.franziskaneumeister.recentflics.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.deviceProtectedDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.franziskaneumeister.recentflics.core.datastore.SettingsSerializer
import de.franziskaneumeister.recentflics.core.datastore.models.Settings
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class DataStoreModule {

    @Provides
    @Singleton
    internal fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Settings> {
        return DataStoreFactory.create(
            serializer = SettingsSerializer,
            produceFile = {
                appContext.deviceProtectedDataStoreFile("settings.json")
            },
        )
    }
}