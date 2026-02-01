package de.franziskaneumeister.recentflics.data.movies

import de.franziskaneumeister.recentflics.core.network.MoviesDataSource
import de.franziskaneumeister.recentflics.core.network.model.MovieApiModel
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class MovieRepository @Inject internal constructor(
    private val dataSource: MoviesDataSource
) {

    public suspend fun loadMovies(): Flow<List<Movie>> {
        return flow{
            val response = dataSource.getMovies()
            val apiModels = response.results
            val movies = apiModels.map { it.toEntity() }
            this.emit(movies)
        }
    }

    private fun MovieApiModel.toEntity(): Movie {
        return Movie(
            id = this.id,
            title = title,
            releaseDate = releaseDate,
            overview = overview
        )
    }
}