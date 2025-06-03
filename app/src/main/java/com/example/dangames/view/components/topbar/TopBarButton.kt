package com.example.dangames.view.components.topbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.dangames.design.theme.DanGamesTheme


@Composable
fun TopBarButton(
    visible: Boolean,
    onClick: () -> Unit,
    @DrawableRes painter: Int,
    containerColor: Color = DanGamesTheme.colors.singleAltColor,
    contentColor: Color = DanGamesTheme.colors.oppositeAltColor
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandIn(
            expandFrom = Alignment.TopStart
        ) + fadeIn(),
        exit = shrinkOut(
            shrinkTowards = Alignment.TopStart
        ) + fadeOut()
    ) {
        IconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Icon(
                painter = painterResource(painter),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
            )
        }
    }
}

@Composable
fun TopBarButton(
    visible: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    containerColor: Color = DanGamesTheme.colors.singleAltColor,
    contentColor: Color = DanGamesTheme.colors.oppositeAltColor
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandIn(
            expandFrom = Alignment.TopEnd
        ) + fadeIn(),
        exit = shrinkOut(
            shrinkTowards = Alignment.TopEnd
        ) + fadeOut()
    ) {
        IconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
            )
        }
    }
}