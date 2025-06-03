package com.example.dangames.view.screen.game_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.dangames.R
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.rating.Rating
import com.example.dangames.view.util.ignorePadding
import com.valentinilk.shimmer.shimmer

@Composable
fun GameDetailsHeader(
    game: Model.Game?,
    scrollableState: ScrollableState,
    scrollOffset: Float,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(horizontal = 12.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = scrollableState
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage(
            game = game,
            offset = scrollOffset,
            onFavoriteClick = onFavoriteClick
        )
        Text(
            text = game?.name ?: "",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        if (game?.released != null)
            Text(
                text = "Дата выхода: " + game.released,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        if (game?.tba == true)
            Text(
                text = "Выходит в ближайшее время",
                fontSize = 16.sp,
                color = Color.White
            )
        if (game?.esrb != null)
            Text(
                text = "ESRB: " + game.esrb.name,
                fontSize = 16.sp,
                color = Color.White
            )
        Text(
            text = game?.genres?.joinToString(", ") { it.name } ?: "",
            modifier = Modifier.padding(horizontal = 100.dp),
            color = DanGamesTheme.colors.hint,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun GameDetailsHeaderShimmer() {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.aspectRatio(0.5f)
        )
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth(0.7f)
                .height(32.dp)
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.8f))
        )
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth(0.45f)
                .height(16.dp)
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f)),
        )
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth(0.6f)
                .height(16.dp)
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f)),
        )
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth(0.4f)
                .height(24.dp)
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f)),
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun HeaderImage(
    game: Model.Game?,
    offset: Float,
    onFavoriteClick: () -> Unit,
) {
    var aspectRatio by remember { mutableFloatStateOf(0f) }
    aspectRatio = (0.5f + offset / -1000f).coerceIn(0.5f, 1.6f)

    val ratings = Pair(game?.metacritic ?: -1, game?.rating ?: -1.0)
    Box(
        modifier = Modifier
            .aspectRatio(aspectRatio)
            .fillMaxWidth()
            .ignorePadding(12.dp)
    ) {
        AsyncImage(
            model = game?.backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(R.drawable.details_overlay),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black, BlendMode.Modulate)
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
        ) {
            AdaptiveRatings(
                firstRating = {
                    Rating(
                        rating = ratings.first,
                        borderless = true,
                        fontSize = 18.sp
                    )
                },
                secondRating = {
                    Rating(
                        rating = ratings.second,
                        borderless = true,
                        fontSize = 18.sp
                    )
                }
            )
        }
    }
}

@Composable
private fun AdaptiveRatings(
    firstRating: @Composable () -> Unit,
    secondRating: @Composable () -> Unit
) {
    SubcomposeLayout { constraints ->
        val measurables = listOf(
            subcompose("first") { firstRating() }[0],
            subcompose("second") { secondRating() }[0]
        )

        val placeables = measurables.map { it.measure(constraints) }

        val maxWidth = maxOf(placeables[0].width, placeables[1].width)

        val updatedMeasurables = listOf(
            subcompose("first_updated") { firstRating() }[0],
            subcompose("second_updated") { secondRating() }[0]
        )

        val updatedPlaceables = updatedMeasurables.map {
            it.measure(constraints.copy(minWidth = maxWidth, maxWidth = maxWidth))
        }

        layout(
            width = maxWidth * 2 + 6.dp.roundToPx(),
            height = maxOf(updatedPlaceables[0].height, updatedPlaceables[1].height)
        ) {
            updatedPlaceables[0].placeRelative(0, 0)
            updatedPlaceables[1].placeRelative(maxWidth + 6.dp.roundToPx(), 0)
        }
    }
}