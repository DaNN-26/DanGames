package com.example.dangames.view.screen.game_details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.topbar.DetailsTopBar
import com.example.dangames.view.screen.game_details.components.GameDetailsHeader
import com.example.dangames.view.screen.game_details.components.GameDetailsHeaderShimmer
import com.example.dangames.view.screen.game_details.components.GameDetailsInfo
import com.example.dangames.view.screen.game_details.viewmodel.GameDetailsEvent
import com.example.dangames.view.screen.game_details.viewmodel.GameDetailsViewModel

@Composable
fun GameDetailsScreen(
    viewModel: GameDetailsViewModel,
    gameId: Int,
    navigateToGameSeries: () -> Unit,
    navigateToAdditionDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Content(
        game = state.currentGame,
        getGameDetails = { viewModel.onEvent(GameDetailsEvent.GetGameDetails(gameId)) },
        updateFavorite = { viewModel.onEvent(GameDetailsEvent.UpdateFavorite) },
        navigateToGameSeries = navigateToGameSeries,
        navigateToAdditionDetails = { navigateToAdditionDetails(it) },
        navigateBack = navigateBack
    )
}

@Composable
private fun Content(
    game: Model.Game?,
    getGameDetails: () -> Unit,
    updateFavorite: () -> Unit,
    navigateToGameSeries: () -> Unit,
    navigateToAdditionDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) { getGameDetails() }

    var offset by remember { mutableFloatStateOf(0f) }
    val scrollableState = rememberScrollableState { delta ->
        val newOffset = offset + delta
        val clampedOffset = newOffset.coerceIn(-1000f, 0f)
        val consumed = clampedOffset - offset
        offset = clampedOffset
        consumed
    }

    val topBarButtonContainerColor by animateColorAsState(
        targetValue = if (offset <= -400f) DanGamesTheme.colors.singleAltColor else Color.Transparent,
        animationSpec = tween(300)
    )

    val topBarButtonContentColor by animateColorAsState(
        targetValue = if (offset <= -400f) DanGamesTheme.colors.opposite else Color.White,
        animationSpec = tween(200)
    )

    Scaffold(
        topBar = {
            DetailsTopBar(
                navigateBack = navigateBack,
                onFavoriteClick = updateFavorite,
                favoriteIcon = if(game?.isFavorite == true)
                    Icons.Default.Favorite
                else Icons.Default.FavoriteBorder,
                buttonContainerColor = topBarButtonContainerColor,
                buttonContentColor = topBarButtonContentColor
            )
        }
    ) { contentPadding -> @Suppress("UNUSED_EXPRESSION") contentPadding
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DanGamesTheme.colors.single)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (game != null) {
                GameDetailsHeader(
                    game = game,
                    scrollableState = scrollableState,
                    scrollOffset = offset,
                    onFavoriteClick = updateFavorite
                )
                GameDetailsInfo(
                    game = game,
                    navigateToGameSeries = navigateToGameSeries,
                    navigateToAdditionDetails = { navigateToAdditionDetails(it) }
                )
            } else GameDetailsHeaderShimmer()
        }
    }
}