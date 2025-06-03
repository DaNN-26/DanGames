package com.example.dangames.view.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.game_item.GameItem
import com.example.dangames.view.components.game_item.GameItemShimmer
import com.example.dangames.view.components.CustomTextField
import com.example.dangames.view.components.snackbar.CustomSnackbar
import com.example.dangames.view.screen.home.viewmodel.HomeEvent
import com.example.dangames.view.screen.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToGameDetails: (gameId: Int) -> Unit,
    navigateToAllGames: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToFavorites: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Content(
        games = state.games,
        getData = { viewModel.onEvent(HomeEvent.GetData) },
        navigateToGameDetails = { navigateToGameDetails(it) },
        navigateToAllGames = navigateToAllGames,
        navigateToSearch = navigateToSearch,
        navigateToFavorites = navigateToFavorites,
        isError = state.isError
    )
}

@Composable
private fun Content(
    games: List<Model.Game>,
    getData: () -> Unit,
    navigateToGameDetails: (gameId: Int) -> Unit,
    navigateToAllGames: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToFavorites: () -> Unit,
    isError: Boolean,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(Unit) { getData() }

    LaunchedEffect(isError) {
        if (isError)
            snackbarHostState.showSnackbar(
                message = "Проверьте подключение к интернету",
                duration = SnackbarDuration.Indefinite
            )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            CustomSnackbar(
                snackbarHostState = snackbarHostState,
                onRefreshClick = { getData() }
            )
        },
    ) { contentPadding ->
        Box {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(DanGamesTheme.colors.single)
                    .padding(horizontal = 12.dp),
                state = lazyStaggeredGridState,
                contentPadding = contentPadding,
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (games.isEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        CustomTextField(
                            onClick = navigateToSearch,
                            placeholder = { Text(text = "Поиск") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            enabled = false
                        )
                    }
                    item(span = StaggeredGridItemSpan.FullLine) { ExpandAllButton(true) }
                    items(6) { GameItemShimmer() }
                } else {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        CustomTextField(
                            onClick = navigateToSearch,
                            placeholder = { Text(text = "Поиск") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            enabled = false
                        )
                    }
                    item(span = StaggeredGridItemSpan.FullLine) { ExpandAllButton { navigateToAllGames() } }
                    items(games) { game ->
                        GameItem(
                            game = game,
                            onClick = { navigateToGameDetails(game.id) }
                        )
                    }
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars))
                    }
                }
            }
            if (games.isNotEmpty())
                AnimatedFavoritesButton(
                    visible = !lazyStaggeredGridState.canScrollForward && lazyStaggeredGridState.canScrollBackward,
                    onClick = navigateToFavorites,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                )
        }
    }
}

@Composable
private fun ExpandAllButton(
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val actionColor = if (isLoading) DanGamesTheme.colors.hint else DanGamesTheme.colors.accent

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Все игры",
            fontSize = 16.sp,
            color = DanGamesTheme.colors.oppositeAltColor
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onClick() }
        ) {
            Text(
                text = "Посмотреть все",
                modifier = Modifier.padding(6.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = actionColor
            )
        }
    }
}

@Composable
private fun AnimatedFavoritesButton(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { it }
        ),
        exit = slideOutVertically { it * 2 } + fadeOut(),
    ) {
        ExtendedFloatingActionButton(
            text = { Text("Избранное") },
            icon = { Icon(Icons.Default.FavoriteBorder, null) },
            onClick = onClick,
            shape = RoundedCornerShape(26.dp),
            containerColor = DanGamesTheme.colors.accent,
            contentColor = Color.White
        )
    }
}