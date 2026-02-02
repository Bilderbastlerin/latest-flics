package de.franziskaneumeister.recentflics.feature.home.list

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import kotlinx.serialization.Serializable

@Serializable
internal data object ListDestination

internal fun NavGraphBuilder.listScreen(goToMovie: (MovieId) -> Unit) {
    composable<ListDestination> {
        val viewModel = hiltViewModel<ListViewModel>()
        ListScreen(viewModel, goToMovie)
    }
}