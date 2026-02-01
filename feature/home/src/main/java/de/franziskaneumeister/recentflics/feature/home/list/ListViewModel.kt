package de.franziskaneumeister.recentflics.feature.home.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.franziskaneumeister.recentflics.data.movies.MovieRepository
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ListViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movies: Flow<List<Movie>> by lazy {
        val mutableSharedFlow = MutableSharedFlow<List<Movie>>(1, 1)
        viewModelScope.launch {
            movieRepository.loadMovies()
                .collect {
                mutableSharedFlow.emit(it)
            }
        }
        mutableSharedFlow.asSharedFlow()
    }

    val uiState: StateFlow<ListUiState> = movies
        .map { movies ->
            movies.map { ListEntry(it.title, it.id.toString()) }
        }
        .map<List<ListEntry>, ListUiState> {
            ListUiState.Success(
                data = it
            )
        }
        .catch { emit(ListUiState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListUiState.Loading
        )

    sealed interface ListUiState {
        object Loading : ListUiState
        data class Error(val throwable: Throwable) : ListUiState
        data class Success(val data: List<ListEntry>) : ListUiState
    }

    data class ListEntry(
        val name: String,
        val id: String,
    )
}