package de.franziskaneumeister.recentflics.core.network

import de.franziskaneumeister.recentflics.core.types.IODispatcher
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.movielists.MovieResultsPageWithDates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class TmdbDataSource @Inject internal constructor(
    private val api: TmdbApi,
    @param:IODispatcher private val dispatcher: CoroutineDispatcher
) {

    public suspend fun loadMovies(page: Int = 1): Unit {
        withContext(dispatcher) {
            val nowPlaying: MovieResultsPageWithDates = api.movieLists.getNowPlaying(
                "en-US",
                page,
                "US"
            )
        }
    }
}