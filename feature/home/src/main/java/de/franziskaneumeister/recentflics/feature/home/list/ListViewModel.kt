package de.franziskaneumeister.recentflics.feature.home.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    movieRepository: MovieRepository
) : ViewModel() {
    val uiState = movieRepository.moviesPager // movieRepository.cachedMoviesPager
        .map { pagingData ->
            pagingData.map {
                ListEntry(
                    id = it.id,
                    title = it.title,
                    releaseDate = it.releaseDate
                )
            }
        }
        .cachedIn(viewModelScope)

    data class ListEntry(
        val title: String,
        val releaseDate: LocalDate,
        val id: MovieId,
    )
}