package de.franziskaneumeister.recentflics.core.types.entities

import java.time.LocalDate

public data class Movie(
    public val id: MovieId,
    public val name: String,
    public val releaseDate: LocalDate,
    public val overview: String
)

public typealias MovieId = Long
