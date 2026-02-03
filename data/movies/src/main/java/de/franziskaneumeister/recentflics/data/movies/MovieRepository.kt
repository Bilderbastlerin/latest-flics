@file:OptIn(ExperimentalPagingApi::class)

package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import de.franziskaneumeister.recentflics.core.database.MovieDao
import de.franziskaneumeister.recentflics.core.database.models.MovieDbModel
import de.franziskaneumeister.recentflics.core.network.model.MovieApiModel
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import de.franziskaneumeister.recentflics.core.types.utils.suspendRunCatching
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
public class MovieRepository @Inject internal constructor(
    private val moviePagingSource: MoviePagingSource,
    private val remoteMediator: MovieRemoteMediator,
    private val movieDao: MovieDao,
) {

    /**
     * Paging data backed by database cache. But can't keep
     * the order
     */
    public val cachedMoviesPager: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = remoteMediator,
        initialKey = 1,
        pagingSourceFactory = {
            movieDao.pagingSource()
        }
    )
        .flow
        .map { pagingData ->
            pagingData.map {
                it.toEntity()
            }
        }

    /**
     * Paging data in order of popularity (as by the web api). But
     * no caching mechanism.
     */
    public val moviesPager: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { moviePagingSource },
        initialKey = 1
    )
        .flow

    public suspend fun getMovie(movieId: MovieId): Result<Movie> {
        return suspendRunCatching {
            movieDao.findMovie(movieId)
        }.map { it.toEntity() }
    }

    private companion object {
        private const val PAGE_SIZE = 20
    }
}

internal fun MovieApiModel.toEntity(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        overview = this.overview
    )
}

internal fun MovieDbModel.toEntity(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        overview = this.overview
    )
}

internal fun MovieApiModel.toDbModel(): MovieDbModel {
    return MovieDbModel(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        overview = this.overview,
        popularity = this.popularity
    )
}
