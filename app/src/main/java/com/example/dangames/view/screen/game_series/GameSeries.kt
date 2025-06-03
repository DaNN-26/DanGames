package com.example.dangames.view.screen.game_series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dangames.view.components.games_grid.GamesGrid
import com.example.dangames.view.screen.game_series.viewmodel.GameSeriesEvent
import com.example.dangames.view.screen.game_series.viewmodel.GameSeriesViewModel

@Composable
fun GameSeriesScreen(
    viewModel: GameSeriesViewModel,
    gameId: Int,
    navigateToGameDetails: (id: Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    GamesGrid(
        games = state.model?.results ?: emptyList(),
        onGameItemClick = { navigateToGameDetails(it) },
        getGames = { viewModel.onEvent(GameSeriesEvent.GetGameSeries(gameId)) },
        topBarTitle = "Игры из серии",
        navigateBack = navigateBack,
        isNetworkError = state.isError
    )
}