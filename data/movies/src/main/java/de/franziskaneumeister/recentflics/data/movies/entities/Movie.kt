package de.franziskaneumeister.recentflics.data.movies.entities

import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import java.time.LocalDate

public data class Movie(
    public val id: MovieId,
    public val title: String,
    public val releaseDate: LocalDate,
    public val overview: String
)
