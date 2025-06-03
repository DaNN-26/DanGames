package com.example.dangames.view.screen.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dangames.view.components.games_grid.GamesGrid
import com.example.dangames.view.screen.favorites.viewmodel.FavoritesEvent
import com.example.dangames.view.screen.favorites.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navigateToGameDetails: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    GamesGrid(
        games = state.favoriteGames,
        onGameItemClick = { navigateToGameDetails(it) },
        getGames = { viewModel.onEvent(FavoritesEvent.GetFavoriteGames) },
        topBarTitle = "Избранное",
        navigateBack = navigateBack,
        isNetworkError = false
    )
}