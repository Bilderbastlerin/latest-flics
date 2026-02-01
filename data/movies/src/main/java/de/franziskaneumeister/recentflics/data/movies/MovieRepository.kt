package de.franziskaneumeister.recentflics.data.movies

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class MovieRepository @Inject internal constructor() {

    public fun loadMovies(): Flow<Map<String, Int>> {
        return flowOf(
            mapOf(
                "foo" to 1,
                "bar" to 2,
                "lorem" to 3,
                "ipsum" to 4
            )
        )
    }
}