package de.franziskaneumeister.recentflics.core.network

import de.franziskaneumeister.recentflics.core.network.model.PagedResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Query

public interface MoviesDataSource {
    @OptIn(InternalSerializationApi::class)
    @GET("movie/now_playing")
    public suspend fun getMovies(@Query("page") page: Int = 1): PagedResponse
}