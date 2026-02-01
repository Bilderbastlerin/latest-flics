@file:UseSerializers(LocalDateSerializer::class)

package de.franziskaneumeister.recentflics.core.network.model

import de.franziskaneumeister.recentflics.core.network.LocalDateSerializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@InternalSerializationApi
@Serializable
public data class PagedResponse(
    @SerialName("total_results")
    public val totalResults: Int,
    @SerialName("total_pages")
    public val totalPages: Int,
    public val page: Int,
    public val results: List<MovieApiModel>
)