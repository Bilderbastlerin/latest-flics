package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.PagingSource
import de.franziskaneumeister.recentflics.core.network.model.MovieApiModel
import de.franziskaneumeister.recentflics.core.types.entities.ApiPage
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class MovieRepository @Inject internal constructor(
    private val moviePagingSource: MoviePagingSource
) {
    public val  moviesForPager: PagingSource<ApiPage, Movie>
        get() = moviePagingSource

}

internal fun MovieApiModel.toEntity(): Movie {
    return Movie(
        id = this.id,
        title = title,
        releaseDate = releaseDate,
        overview = overview
    )
}
