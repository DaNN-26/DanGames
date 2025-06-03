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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dangames.R
import com.example.dangames.design.theme.DanGamesTheme

@Composable
fun GridTopBar(
    title: String,
    navigateBack: () -> Unit,
    changeGridCells: () -> Unit,
    gridCells: Int,
    isScrollInInitialState: () -> Boolean,
    lastScrolledBackward: Boolean
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
            visible = isScrollInInitialState.invoke() || lastScrolledBackward,
            onClick = navigateBack,
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft
        )
        Text(
            text = if (isScrollInInitialState.invoke()) title else "",
            fontSize = 16.sp,
            color = DanGamesTheme.colors.opposite
        )
        TopBarButton(
            visible = isScrollInInitialState.invoke() || lastScrolledBackward,
            onClick = changeGridCells,
            painter = R.drawable.dashboard,
            contentColor = if (gridCells == 1)
                DanGamesTheme.colors.hint.copy(alpha = 0.5f)
            else DanGamesTheme.colors.oppositeAltColor,
        )
    }
}