@file:OptIn(ExperimentalPagingApi::class)

package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import de.franziskaneumeister.recentflics.core.database.MovieDao
import de.franziskaneumeister.recentflics.core.database.models.MovieDbModel
import de.franziskaneumeister.recentflics.core.network.model.MovieApiModel
import de.franziskaneumeister.recentflics.core.types.entities.ApiPage
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
    public val moviesForPager: PagingSource<ApiPage, Movie>
        get() = moviePagingSource

    public val moviesPager: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = remoteMediator,
        initialKey = 1,
        pagingSourceFactory = {
            movieDao.pagingSource()
        }
    )
        .flow
        .map { pagingData -> pagingData.map {
            it.toEntity()
        } }
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
