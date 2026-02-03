@file:OptIn(InternalSerializationApi::class)

package de.franziskaneumeister.recentflics.core.datastore

import androidx.datastore.core.DataStore
import de.franziskaneumeister.recentflics.core.datastore.models.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

public class SettingsDataSource
@Inject internal constructor(
    private val dataStore: DataStore<Settings>
) {
    public val settings: Flow<Settings> = dataStore.data

    public suspend fun updateSettings(block: (Settings) -> Settings) {
        dataStore.updateData {
            block(it)
        }
    }

}