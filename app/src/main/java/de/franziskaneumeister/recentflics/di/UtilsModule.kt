package de.franziskaneumeister.recentflics.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.franziskaneumeister.recentflics.core.types.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
public abstract class UtilsModule {

    public companion object {

        @Provides
        @IODispatcher
        internal fun providesDispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }
}