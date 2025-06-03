package com.example.dangames.view.components.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme

@Composable
fun DetailsTopBar(
    navigateBack: () -> Unit,
    onFavoriteClick: () -> Unit,
    favoriteIcon: ImageVector,
    buttonContainerColor: Color = DanGamesTheme.colors.singleAltColor,
    buttonContentColor: Color = DanGamesTheme.colors.oppositeAltColor,
) {
    Row(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarButton(
            visible = true,
            onClick = navigateBack,
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            containerColor = buttonContainerColor,
            contentColor = buttonContentColor
        )
        TopBarButton(
            visible = true,
            onClick = onFavoriteClick,
            icon = favoriteIcon,
            containerColor = buttonContainerColor,
            contentColor = buttonContentColor
        )
    }
}