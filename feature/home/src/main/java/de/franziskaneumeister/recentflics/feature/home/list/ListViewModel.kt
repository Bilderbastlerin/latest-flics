package de.franziskaneumeister.recentflics.feature.home.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.franziskaneumeister.recentflics.data.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class ListViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movies: Flow<Map<String, Int>> by lazy {
        flowOf(
            mapOf(
                "foo" to 1,
                "bar" to 2,
                "lorem" to 3,
                "ipsum" to 4
            )
        )
    }

    val uiState: StateFlow<ListUiState> = movies
        .map { movies ->
            movies.map { ListEntry(it.key, it.value.toString()) }
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