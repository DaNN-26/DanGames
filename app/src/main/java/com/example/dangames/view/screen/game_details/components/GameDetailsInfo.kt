package com.example.dangames.view.screen.game_details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.image.AsyncImageWithProgress
import com.example.dangames.view.util.ignorePadding
import com.valentinilk.shimmer.shimmer

@Composable
fun GameDetailsInfo(
    game: Model.Game,
    navigateToGameSeries: () -> Unit,
    navigateToAdditionDetails: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (game.gameSeriesCount > 0)
            TextButton(
                onClick = navigateToGameSeries,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = DanGamesTheme.colors.accent,
                )
            ) {
                Text(
                    text = "Показать игры серии",
                    fontSize = 16.sp
                )
            }
        ExpandableInfoItem(
            title = "Описание:",
            value = HtmlCompat.fromHtml(game.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        )
        if (game.shortScreenshots?.isNotEmpty() == true)
            DetailsPager(
                list = game.shortScreenshots,
                title = "Скриншоты:",
            ) { item ->
                item as Model.Screenshot
                AsyncImageWithProgress(
                    imagePath = item.image,
                    modifier = Modifier.aspectRatio(16f / 9f)
                )
            }
        InfoItem(
            title = "Игровые платформы:",
            value = game.platforms.joinToString(", ") { it.name }
        )
        if (game.stores.isNotEmpty())
            InfoItem(
                title = "Доступно в магазинах:",
                value = game.stores.joinToString(", ") { it.name }
            )
        if (game.additionsCount > 0)
            DetailsPager(
                list = game.additions,
                title = "Дополнения и другие издания:",
                itemShimmer = { AdditionItemShimmer() }
            ) { item ->
                item as Model.Game
                AdditionItem(
                    addition = item,
                    onClick = { navigateToAdditionDetails(item.id) }
                )
            }
        InfoItem(
            title = "Теги:",
            value = game.tags.joinToString(", ") { it.name }
        )
        Spacer(Modifier.height(18.dp))
    }
}

@Composable
private fun InfoItem(
    title: String,
    value: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = DanGamesTheme.colors.oppositeAltColor
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = DanGamesTheme.colors.opposite
        )
    }
}

@Composable
private fun ExpandableInfoItem(
    title: String,
    value: String,
) {
    var expanded by remember { mutableStateOf(false) }
    var isExpandable by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = DanGamesTheme.colors.oppositeAltColor
        )
        Text(
            text = value,
            modifier = Modifier.animateContentSize(),
            fontSize = 16.sp,
            color = DanGamesTheme.colors.opposite,
            overflow = TextOverflow.Ellipsis,
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            onTextLayout = { if (it.hasVisualOverflow) isExpandable = true }
        )
        if (isExpandable)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.weight(1f),
                    color = DanGamesTheme.colors.oppositeAltColor
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = DanGamesTheme.colors.oppositeAltColor
                    )
                ) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.weight(1f),
                    color = DanGamesTheme.colors.oppositeAltColor
                )
            }
    }
}

@Composable
private fun DetailsPager(
    list: List<Any>,
    title: String,
    itemShimmer: @Composable () -> Unit = {},
    item: @Composable (item: Any) -> Unit,
) {
    val pagerState = rememberPagerState { list.size }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = DanGamesTheme.colors.oppositeAltColor
        )
        if (list.isEmpty())
            itemShimmer()
        else
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .ignorePadding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                item(list[page])
            }
        if (pagerState.pageCount > 1)
            PagerIndicator(
                pagesSize = pagerState.pageCount,
                currentPage = pagerState.currentPage,
            )
    }
}

@Composable
private fun AdditionItem(
    addition: Model.Game,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImageWithProgress(
            imagePath = addition.backgroundImage ?: "",
            modifier = Modifier.aspectRatio(16f / 9f)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = addition.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = DanGamesTheme.colors.opposite
        )
        Text(
            text = addition.released ?: "Дата релиза неизвестна",
            color = DanGamesTheme.colors.hint,
        )
    }
}

@Composable
private fun AdditionItemShimmer() {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .shimmer()
                .aspectRatio(16f / 9f)
                .background(DanGamesTheme.colors.hint)
        )
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .height(16.dp)
                .fillMaxWidth()
                .background(DanGamesTheme.colors.hint)
        )
        Spacer(Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .shimmer()
                .height(14.dp)
                .fillMaxWidth()
                .background(DanGamesTheme.colors.hint)
        )
    }
}

@Composable
private fun PagerIndicator(
    pagesSize: Int,
    currentPage: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        repeat(pagesSize) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (it == currentPage)
                            DanGamesTheme.colors.accent
                        else DanGamesTheme.colors.hint.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}