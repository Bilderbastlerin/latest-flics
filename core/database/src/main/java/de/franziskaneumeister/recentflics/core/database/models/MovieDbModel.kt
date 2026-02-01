package de.franziskaneumeister.recentflics.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import java.time.LocalDate


@Entity(tableName = "movies")
public data class MovieDbModel(
    @PrimaryKey
    val id: MovieId,
    val title: String,
    val releaseDate: LocalDate,
    val overview: String,
)
