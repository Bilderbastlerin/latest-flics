package de.franziskaneumeister.recentflics.feature.movie.details

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import kotlinx.serialization.Serializable

@Serializable
public data class DetailsRoute(
    val id: MovieId
)

public fun NavGraphBuilder.detailsScreen(goBack: () -> Unit) {
    composable<DetailsRoute> {
        val viewModel = hiltViewModel<DetailsViewModel>()
        DetailsScreen(viewModel, goBack)
    }
}