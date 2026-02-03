package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import de.franziskaneumeister.recentflics.core.database.MovieDao
import de.franziskaneumeister.recentflics.core.network.MoviesDataSource
import de.franziskaneumeister.recentflics.core.types.entities.ApiPage
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import javax.inject.Inject

internal class MoviePagingSource @Inject constructor(
    private val moviesDataSource: MoviesDataSource,
    private val movieDao: MovieDao
) : PagingSource<ApiPage, Movie>() {
    override suspend fun load(params: LoadParams<ApiPage>): LoadResult<ApiPage, Movie> {
        val nextPageNumber = params.key ?: 1
        try {
            val pagedResponse = moviesDataSource.getMovies(nextPageNumber)
            val apiModels = pagedResponse.results

            val moviesForDatabase = apiModels.map { model -> model.toDbModel() }
            movieDao.insertAll(moviesForDatabase)

            val movies = apiModels.map { model -> model.toEntity() }

            return LoadResult.Page(
                data = movies,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey =  if (nextPageNumber < pagedResponse.totalPages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<ApiPage, Movie>): ApiPage? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}