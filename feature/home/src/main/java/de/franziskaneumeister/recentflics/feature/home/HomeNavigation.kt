package de.franziskaneumeister.recentflics.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import de.franziskaneumeister.recentflics.feature.home.list.ListDestination
import de.franziskaneumeister.recentflics.feature.home.list.listScreen
import kotlinx.serialization.Serializable

@Serializable
public data object HomeRoute

public fun NavGraphBuilder.homeSection(goToMovie: (MovieId) -> Unit) {
    navigation<HomeRoute>(startDestination = ListDestination ) {
        listScreen(goToMovie)
    }
}
