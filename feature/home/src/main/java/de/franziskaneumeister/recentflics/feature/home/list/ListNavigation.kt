package de.franziskaneumeister.recentflics.feature.home.list

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object ListDestination

internal fun NavGraphBuilder.listScreen() {
    composable<ListDestination> {
        val viewModel = hiltViewModel<ListViewModel>()
        ListScreen(viewModel, { TODO("navigate to movie")})
    }
}