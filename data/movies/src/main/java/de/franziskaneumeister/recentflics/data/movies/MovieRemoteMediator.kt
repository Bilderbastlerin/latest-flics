@file:OptIn(ExperimentalPagingApi::class)

package de.franziskaneumeister.recentflics.data.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import de.franziskaneumeister.recentflics.core.database.MovieDao
import de.franziskaneumeister.recentflics.core.database.TransactionHandler
import de.franziskaneumeister.recentflics.core.database.models.MovieDbModel
import de.franziskaneumeister.recentflics.core.datastore.SettingsDataSource
import de.franziskaneumeister.recentflics.core.network.MoviesDataSource
import de.franziskaneumeister.recentflics.core.network.model.MovieApiModel
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class MovieRemoteMediator @Inject constructor(
    private val movieDao: MovieDao,
    private val transactionHandler: TransactionHandler,
    private val networkService: MoviesDataSource,
    private val settingsDataSource: SettingsDataSource
) : RemoteMediator<MovieId, MovieDbModel>() {

    private var remoteKey = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<MovieId, MovieDbModel>
    ): MediatorResult {
        return try {
            val nextPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey + 1
                }
            }

            val response = networkService.getMovies(nextPage)

            transactionHandler.performTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                    remoteKey = 1
                }

                val movieDbModels = response.results.map { it.toDbModel() }
                movieDao.insertAll(movieDbModels)
                remoteKey = nextPage
            }

            MediatorResult.Success(
                endOfPaginationReached = false //nextPage >= response.totalPages
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH // always refresh the cache at the start of the app
    }
}

private fun MovieApiModel.toDbModel(): MovieDbModel {
    return MovieDbModel(
        id = this.id,
        title = this.title,
        releaseDate = this.releaseDate,
        overview = this.overview
    )
}
