package com.example.dangames.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dangames.view.screen.all_games.AllGamesScreen
import com.example.dangames.view.screen.all_games.viewmodel.AllGamesViewModel
import com.example.dangames.view.screen.favorites.FavoritesScreen
import com.example.dangames.view.screen.favorites.viewmodel.FavoritesViewModel
import com.example.dangames.view.screen.game_details.GameDetailsScreen
import com.example.dangames.view.screen.game_details.viewmodel.GameDetailsViewModel
import com.example.dangames.view.screen.game_series.GameSeriesScreen
import com.example.dangames.view.screen.game_series.viewmodel.GameSeriesViewModel
import com.example.dangames.view.screen.home.HomeScreen
import com.example.dangames.view.screen.home.viewmodel.HomeViewModel
import com.example.dangames.view.screen.search.SearchScreen
import com.example.dangames.view.screen.search.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DanGamesNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home
    ) {
        composable<NavDestination.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navigateToGameDetails = { gameId ->
                    navController.navigate(NavDestination.GameDetails(gameId))
                },
                navigateToAllGames = { navController.navigate(NavDestination.AllGames()) { launchSingleTop = true} },
                navigateToSearch = { navController.navigate(NavDestination.Search) { launchSingleTop = true } },
                navigateToFavorites = { navController.navigate(NavDestination.Favorites) { launchSingleTop = true } }
            )
        }
        composable<NavDestination.AllGames>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(500)
                ) + expandHorizontally(animationSpec = tween(200))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(700))
            }
        ) {
            val viewModel = koinViewModel<AllGamesViewModel>()
            val route = it.toRoute<NavDestination.AllGames>()
            AllGamesScreen(
                viewModel = viewModel,
                query = route.query,
                genres = route.genres,
                navigateToGameDetails = { gameId ->
                    navController.navigate(NavDestination.GameDetails(gameId))
                },
                navigateBack = { navController.safePopBackStack() }
            )
        }
        composable<NavDestination.GameDetails> {
            val viewModel = koinViewModel<GameDetailsViewModel>()
            val route = it.toRoute<NavDestination.GameDetails>()
            GameDetailsScreen(
                viewModel = viewModel,
                gameId = route.id,
                navigateToGameSeries = {
                    navController.navigate(
                        NavDestination.GameSeries(
                            route.id
                        )
                    )
                },
                navigateToAdditionDetails = { gameId ->
                    navController.navigate(NavDestination.GameDetails(gameId))
                },
                navigateBack = { navController.safePopBackStack() }
            )
        }
        composable<NavDestination.GameSeries> {
            val viewModel = koinViewModel<GameSeriesViewModel>()
            val route = it.toRoute<NavDestination.GameSeries>()
            GameSeriesScreen(
                viewModel = viewModel,
                gameId = route.id,
                navigateToGameDetails = { gameId ->
                    navController.navigate(NavDestination.GameDetails(gameId))
                },
                navigateBack = { navController.safePopBackStack() }
            )
        }
        composable<NavDestination.Search>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(500)
                ) + expandHorizontally { it / 2 }
            },
            popEnterTransition = {
                fadeIn(tween(600))
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(600)
                ) + shrinkHorizontally(animationSpec = tween(600)) { it / 2 }
            }
        ) {
            val viewModel = koinViewModel<SearchViewModel>()
            SearchScreen(
                viewModel = viewModel,
                navigateToGames = { query, genres ->
                    navController.navigate(NavDestination.AllGames(query, genres)) { launchSingleTop = true }
                },
                navigateBack = { navController.safePopBackStack() }
            )
        }
        composable<NavDestination.Favorites>(
            enterTransition = {
                slideInVertically { it }
            },
            popEnterTransition = {
                fadeIn(tween(600))
            },
            popExitTransition = {
                slideOutVertically(tween(600)) { it }
            }
        ) {
            val viewModel = koinViewModel<FavoritesViewModel>()
            FavoritesScreen(
                viewModel = viewModel,
                navigateToGameDetails = { gameId ->
                    navController.navigate(NavDestination.GameDetails(gameId))
                },
                navigateBack = { navController.safePopBackStack() }
            )
        }
    }
}

private fun NavController.safePopBackStack() {
    if (previousBackStackEntry != null) popBackStack()
}
