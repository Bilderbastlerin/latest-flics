package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import de.franziskaneumeister.recentflics.core.network.MoviesDataSource
import de.franziskaneumeister.recentflics.core.types.entities.ApiPage
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import javax.inject.Inject

internal class MoviePagingSource @Inject constructor(
    private val moviesDataSource: MoviesDataSource
) : PagingSource<ApiPage, Movie>() {
    override suspend fun load(params: LoadParams<ApiPage>): LoadResult<ApiPage, Movie> {
        val nextPageNumber = params.key ?: 1
        try {
            val pagedResponse = moviesDataSource.getMovies(nextPageNumber)
            return LoadResult.Page(
                data = pagedResponse.results.map { model -> model.toEntity() },
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