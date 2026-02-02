package de.franziskaneumeister.recentflics.feature.home.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import de.franziskaneumeister.recentflics.data.movies.MovieRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class ListViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val pager = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
        ),
        initialKey = 1,
        pagingSourceFactory = { movieRepository.moviesForPager }
    )
    public val uiState = movieRepository.moviesPager
        .map { pagingData ->
            pagingData.map {
                ListEntry(id = it.id, title = it.title, releaseDate = it.releaseDate)
            }
        }
        .cachedIn(viewModelScope)

    //    val uiState: StateFlow<ListUiState> = movies
//        .map { movies ->
//            movies.map { ListEntry(it.title, it.id.toString()) }
//        }
//        .map<List<ListEntry>, ListUiState> {
//            ListUiState.Success(
//                data = it
//            )
//        }
//        .catch { emit(ListUiState.Error(it)) }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = ListUiState.Loading
//        )
//
//    sealed interface ListUiState {
//        object Loading : ListUiState
//        data class Error(val throwable: Throwable) : ListUiState
//        data class Success(val data: List<ListEntry>) : ListUiState
//    }
//
    data class ListEntry(
        val title: String,
        val releaseDate: LocalDate,
        val id: MovieId,
    )
}