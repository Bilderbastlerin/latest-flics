package de.franziskaneumeister.recentflics.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.franziskaneumeister.recentflics.feature.home.list.ListDestination
import de.franziskaneumeister.recentflics.feature.home.list.listScreen
import kotlinx.serialization.Serializable

@Serializable
public data object HomeDestination

public fun NavGraphBuilder.homeSection() {
    navigation<HomeDestination>(startDestination = ListDestination ) {
        listScreen()
    }
}
