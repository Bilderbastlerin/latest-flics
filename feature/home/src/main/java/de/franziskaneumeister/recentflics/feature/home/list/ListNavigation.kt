package de.franziskaneumeister.recentflics.feature.home.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object ListDestination
internal fun NavGraphBuilder.listScreen(): Unit {
    composable<ListDestination> {
        ListScreen()
    }
}