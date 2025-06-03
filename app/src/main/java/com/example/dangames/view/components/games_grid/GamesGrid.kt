package com.example.dangames.view.components.games_grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.game_item.GameItem
import com.example.dangames.view.components.game_item.GameItemShimmer
import com.example.dangames.view.components.pagination.PaginationIndicator
import com.example.dangames.view.components.snackbar.CustomSnackbar
import com.example.dangames.view.components.topbar.GridTopBar
import kotlinx.coroutines.launch

@Composable
fun GamesGrid(
    games: List<Model.Game>?,
    onGameItemClick: (Int) -> Unit,
    getGames: () -> Unit,
    topBarTitle: String,
    currentPage: Int = 0,
    pagesCount: Int = 0,
    changePage: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    isNetworkError: Boolean,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    var gridCells by remember { mutableIntStateOf(2) }

    LaunchedEffect(currentPage) { getGames() }

    LaunchedEffect(isNetworkError) {
        if (isNetworkError)
            snackbarHostState.showSnackbar(
                message = "Проверьте подключение к интернету",
                duration = SnackbarDuration.Indefinite
            )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GridTopBar(
                title = topBarTitle,
                navigateBack = navigateBack,
                changeGridCells = { gridCells = if (gridCells == 2) 1 else 2 },
                gridCells = gridCells,
                isScrollInInitialState = { lazyStaggeredGridState.firstVisibleItemScrollOffset == 0 },
                lastScrolledBackward = lazyStaggeredGridState.lastScrolledBackward
            )
        },
        snackbarHost = {
            CustomSnackbar(
                snackbarHostState = snackbarHostState,
                onRefreshClick = { getGames() }
            )
        }
    ) { contentPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(gridCells),
            modifier = Modifier
                .fillMaxSize()
                .background(DanGamesTheme.colors.single)
                .padding(horizontal = 12.dp),
            state = lazyStaggeredGridState,
            contentPadding = contentPadding,
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (games == null)
                items(6) { GameItemShimmer() }
            else {
                items(games) { game ->
                    GameItem(
                        game = game,
                        onClick = { onGameItemClick(game.id) },
                        needToRestart = gridCells == 1
                    )
                }
                if (currentPage != 0)
                    item(span = StaggeredGridItemSpan.FullLine) {
                        PaginationIndicator(
                            currentPage = currentPage,
                            totalPages = pagesCount,
                            onPageChange = {
                                coroutineScope.launch {
                                    changePage(it)
                                    if (lazyStaggeredGridState.canScrollBackward)
                                        lazyStaggeredGridState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
            }
        }
    }
}