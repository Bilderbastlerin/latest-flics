package de.franziskaneumeister.recentflics.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.franziskaneumeister.recentflics.feature.home.list.listScreen

public fun NavGraphBuilder.homeSection(navController: NavController): Unit {
    navigation("list", "home") {
        listScreen()
    }
}
