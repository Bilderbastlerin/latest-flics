package de.franziskaneumeister.recentflics

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import de.franziskaneumeister.recentflics.feature.home.homeSection

@Composable
internal fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        homeSection(navController)
    }
}