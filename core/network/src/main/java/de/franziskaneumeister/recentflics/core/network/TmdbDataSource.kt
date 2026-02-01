package de.franziskaneumeister.recentflics.core.network

import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.movielists.MovieResultsPageWithDates
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

public class TmdbDataSource @Inject internal constructor(
    private val api: TmdbApi,
  //  private val context: CoroutineContext
) {

    public suspend fun loadMovies(page: Int = 1): Unit {
    //    withContext(context) {
        val nowPlaying: MovieResultsPageWithDates = api.movieLists.getNowPlaying(
            "en-US",
            page,
            "US"
        )
        //  }
    }
}