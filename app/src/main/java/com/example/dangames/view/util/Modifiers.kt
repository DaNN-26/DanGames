package com.example.dangames.view.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

@Composable
fun Modifier.ignorePadding(padding: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(
        constraints.copy(
            maxWidth = constraints.maxWidth + 2 * padding.roundToPx(),
        )
    )
    layout(placeable.width, placeable.height) {
        placeable.place(0, 0)
    }
}