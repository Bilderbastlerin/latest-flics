package de.franziskaneumeister.recentflics.feature.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.franziskaneumeister.recentflics.data.movies.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieId = stateHandle.toRoute<DetailsRoute>().id

    val uiState: StateFlow<DetailsUiState> = flow {
        val result = movieRepository.getMovie(movieId)
        emit(result)
    }
        .map { result ->
            result.fold(
                onSuccess = { DetailsUiState.Success(
                    title = it.title,
                    releaseDate = it.releaseDate,
                    bodyText = it.overview
                )},
                onFailure = { DetailsUiState.Error(it)}
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsUiState.Loading
        )

    sealed interface DetailsUiState {
        object Loading : DetailsUiState
        data class Error(val throwable: Throwable) : DetailsUiState
        data class Success(
            val title: String,
            val releaseDate: LocalDate,
            val bodyText: String
        ) : DetailsUiState
    }

}