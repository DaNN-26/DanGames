package com.example.dangames.view.screen.all_games

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dangames.view.components.games_grid.GamesGrid
import com.example.dangames.view.screen.all_games.viewmodel.AllGamesEvent
import com.example.dangames.view.screen.all_games.viewmodel.AllGamesViewModel

@Composable
fun AllGamesScreen(
    viewModel: AllGamesViewModel,
    query: String,
    genres: List<Int>,
    navigateToGameDetails: (id: Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    GamesGrid(
        games = state.model?.results,
        onGameItemClick = { navigateToGameDetails(it) },
        getGames = { viewModel.onEvent(AllGamesEvent.GetGames(query, genres)) },
        topBarTitle = if(state.model?.results?.isEmpty() == true)
            "Ничего не найдено"
        else "Страница: ${state.currentPage}",
        currentPage = state.currentPage,
        pagesCount = state.pagesCount,
        changePage = { viewModel.onEvent(AllGamesEvent.ChangePage(it)) },
        navigateBack = navigateBack,
        isNetworkError = state.isError
    )
}