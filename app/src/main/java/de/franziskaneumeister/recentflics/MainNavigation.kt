package de.franziskaneumeister.recentflics

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import de.franziskaneumeister.recentflics.feature.home.HomeRoute
import de.franziskaneumeister.recentflics.feature.home.homeSection
import de.franziskaneumeister.recentflics.feature.movie.details.DetailsRoute
import de.franziskaneumeister.recentflics.feature.movie.details.detailsScreen

@Composable
internal fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeRoute) {
        homeSection({ movieId ->
            navController.navigate(DetailsRoute(movieId))
        })
        detailsScreen({
            navController.navigateUp()
        })
    }
}