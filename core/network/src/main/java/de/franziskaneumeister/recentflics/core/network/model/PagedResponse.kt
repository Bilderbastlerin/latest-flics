package de.franziskaneumeister.recentflics.core.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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