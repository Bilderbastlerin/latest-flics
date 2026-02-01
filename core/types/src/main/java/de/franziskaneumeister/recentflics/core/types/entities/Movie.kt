package de.franziskaneumeister.recentflics.core.types.entities

public data class Movie(
    public val id: MovieId,
    public val name: String
)

public typealias MovieId = String
