package de.franziskaneumeister.recentflics.feature.home.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal fun NavGraphBuilder.listScreen(): Unit {
    composable("list") {
        ListScreen()
    }
}