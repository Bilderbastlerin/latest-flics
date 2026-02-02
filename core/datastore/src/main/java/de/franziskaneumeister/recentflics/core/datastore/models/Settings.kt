@file:OptIn(InternalSerializationApi::class)

package de.franziskaneumeister.recentflics.core.datastore.models

import de.franziskaneumeister.recentflics.core.types.entities.ApiPage
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
public data class Settings(
    val nextPage: ApiPage
)
