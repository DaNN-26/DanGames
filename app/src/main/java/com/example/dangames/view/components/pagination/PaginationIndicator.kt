package com.example.dangames.view.components.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dangames.design.theme.DanGamesTheme
import java.lang.Integer.max
import kotlin.math.min

@Composable
fun PaginationIndicator(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val maxVisiblePages = 3

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val startPage = max(1, min(currentPage - maxVisiblePages / 2, totalPages - maxVisiblePages + 1))
        val endPage = min(startPage + maxVisiblePages - 1, totalPages)

        if (startPage > 1) {
            PageButton(1, onPageChange)
            if (startPage > 2)
                Text(
                    text = "...",
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DanGamesTheme.colors.opposite
                )
        }

        for (page in startPage..endPage) {
            PageButton(page, onPageChange, page == currentPage)
        }

        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                Text(
                    text = "...",
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DanGamesTheme.colors.opposite
                )
            }
            PageButton(totalPages, onPageChange)
        }
    }
}

@Composable
private fun PageButton(
    page: Int,
    onClick: (Int) -> Unit,
    isSelected: Boolean = false,
) {
    val containerColor = if (isSelected) DanGamesTheme.colors.accent else Color.Transparent
    val contentColor = if (isSelected) Color.White else DanGamesTheme.colors.opposite
    val fontSize = if (page > 10000) 11.sp else 14.sp

    IconButton(
        onClick = { onClick(page) },
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .size(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = page.toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.Medium
        )
    }
}