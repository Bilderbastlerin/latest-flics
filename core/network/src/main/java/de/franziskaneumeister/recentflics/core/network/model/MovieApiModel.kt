@file:UseSerializers(LocalDateSerializer::class)

package de.franziskaneumeister.recentflics.core.network.model

import de.franziskaneumeister.recentflics.core.network.LocalDateSerializer
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@InternalSerializationApi
@Serializable
public data class MovieApiModel(
    public val id: MovieId,
    public val name: String,
    public val releaseDate: LocalDate,
    public val overview: String
)