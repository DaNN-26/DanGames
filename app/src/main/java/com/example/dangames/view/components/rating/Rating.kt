package com.example.dangames.view.components.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme


@Composable
fun Rating(
    rating: Int,
    modifier: Modifier = Modifier,
    borderless: Boolean = false,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    val ratingColor = when (rating) {
        in 0..49 -> DanGamesTheme.colors.redColor
        in 50..74 -> DanGamesTheme.colors.yellowColor
        in 75..100 -> DanGamesTheme.colors.greenColor
        else -> DanGamesTheme.colors.hint
    }
    val updatedModifier = if (borderless)
        Modifier.background(
            color = ratingColor.copy(alpha = 0.5f),
            shape = RoundedCornerShape(6.dp)
        )
    else
        Modifier.border(
            width = 1.dp,
            color = ratingColor.copy(alpha = 0.5f),
            shape = RoundedCornerShape(6.dp)
        )
    Box(
        modifier = updatedModifier.then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (rating == -1) "?" else rating.toString(),
            fontSize = fontSize,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp),
            fontWeight = FontWeight.Bold,
            color = ratingColor
        )
    }
}

@Composable
fun Rating(
    rating: Double,
    modifier: Modifier = Modifier,
    borderless: Boolean = false,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    val ratingColor = when (rating) {
        in 0.1..2.99 -> DanGamesTheme.colors.redColor
        in 3.0..3.79 -> DanGamesTheme.colors.yellowColor
        in 3.8..5.0 -> DanGamesTheme.colors.greenColor
        else -> DanGamesTheme.colors.hint
    }
    val updatedModifier = if (borderless)
        Modifier.background(
            color = ratingColor.copy(alpha = 0.5f),
            shape = RoundedCornerShape(6.dp)
        )
    else
        Modifier.border(
            width = 1.dp,
            color = ratingColor.copy(alpha = 0.5f),
            shape = RoundedCornerShape(6.dp)
        )
    Box(
        modifier = updatedModifier.then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (rating == -1.0 || rating == 0.0) "?" else rating.toString(),
            fontSize = fontSize,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
            fontWeight = FontWeight.Bold,
            color = ratingColor
        )
    }
}
