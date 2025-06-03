package com.example.dangames.view.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.CustomTextField
import com.example.dangames.view.screen.search.viewmodel.SearchEvent
import com.example.dangames.view.screen.search.viewmodel.SearchViewModel
import com.valentinilk.shimmer.shimmer

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToGames: (String, List<Int>) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Content(
        query = state.query,
        onQueryChange = { viewModel.onEvent(SearchEvent.UpdateQuery(it)) },
        genres = state.genres,
        selectGenre = { viewModel.onEvent(SearchEvent.SelectGenre(it)) },
        navigateToGames = {
            navigateToGames(
                state.query,
                state.genres.filter { it.selected }.map { it.id })
        },
        navigateBack = navigateBack
    )
}

@Composable
private fun Content(
    query: String,
    onQueryChange: (String) -> Unit,
    genres: List<Model.Genre>,
    selectGenre: (Model.Genre) -> Unit,
    navigateToGames: () -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                value = query,
                onValueChange = { onQueryChange(it) },
                onIconClick = navigateBack,
                enabled = genres.isNotEmpty(),
            )
        }
    ) { contentPadding ->
        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(DanGamesTheme.colors.single)
                    .padding(contentPadding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "Ввести можно только английские буквы, цифры и пробелы",
                        color = DanGamesTheme.colors.hint,
                    )
                }
                stickyHeader {
                    Text(
                        text = "Жанры",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DanGamesTheme.colors.opposite
                    )
                }
                if (genres.isNotEmpty())
                    items(genres) { genre ->
                        Genre(
                            genre = genre,
                            onCheckedChange = { selectGenre(genre) }
                        )
                    }
                else
                    items(16) { GenreShimmer() }
            }
            AnimatedSearchButton(
                visible = query.isNotBlank() || genres.any { it.selected },
                onClick = navigateToGames,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 24.dp)
                    .height(50.dp)
            )
        }
    }
}

@Composable
private fun Genre(
    genre: Model.Genre,
    onCheckedChange: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Checkbox(
            checked = genre.selected,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                uncheckedColor = DanGamesTheme.colors.hint.copy(alpha = 0.5f),
                checkedColor = DanGamesTheme.colors.accent,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = genre.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = DanGamesTheme.colors.opposite
        )
    }
}

@Composable
private fun GenreShimmer() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.shimmer(),
            colors = CheckboxDefaults.colors(
                uncheckedColor = DanGamesTheme.colors.hint.copy(alpha = 0.5f),
                checkedColor = DanGamesTheme.colors.accent,
                checkmarkColor = Color.White
            )
        )
        Box(
            modifier = Modifier
                .shimmer()
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f))
                .fillMaxWidth(0.8f)
                .height(20.dp)
        )
    }
}

@Composable
private fun SearchTopBar(
    value: String,
    onValueChange: (String) -> Unit,
    onIconClick: () -> Unit,
    enabled: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        IconButton(
            onClick = onIconClick,
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = DanGamesTheme.colors.singleAltColor,
                contentColor = DanGamesTheme.colors.opposite
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
            )
        }
        val allowedRegex = Regex("^[a-zA-Z0-9 ]*$")
        CustomTextField(
            value = value,
            onValueChange = {
                if (it.matches(allowedRegex))
                    onValueChange(it)
            },
            placeholder = { Text(text = "Поиск") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            enabled = enabled
        )
    }
}

@Composable
private fun AnimatedSearchButton(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { it }
        ),
        exit = slideOutVertically { it * 2 } + fadeOut(),
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DanGamesTheme.colors.accent,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Найти"
            )
        }
    }
}