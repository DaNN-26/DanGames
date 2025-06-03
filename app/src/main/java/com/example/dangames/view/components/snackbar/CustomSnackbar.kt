package com.example.dangames.view.components.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    onRefreshClick: () -> Unit
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Snackbar(
            action = {
                IconButton(
                    onClick = onRefreshClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = DanGamesTheme.colors.accent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = DanGamesTheme.colors.singleAltColor,
            contentColor = DanGamesTheme.colors.opposite
        ) {
            Text(
                text = it.visuals.message,
                fontWeight = FontWeight.Medium
            )
        }
    }
}