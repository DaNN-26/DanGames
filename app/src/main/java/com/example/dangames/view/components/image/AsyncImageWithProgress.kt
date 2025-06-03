package com.example.dangames.view.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.dangames.design.theme.DanGamesTheme

@Composable
fun AsyncImageWithProgress(
    imagePath: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    restart: @Composable (AsyncImagePainter) -> Unit = {},
) {
    val painter = rememberAsyncImagePainter(imagePath)
    val painterState by painter.state.collectAsState()

    restart(painter)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (painterState) {
            is AsyncImagePainter.State.Loading ->
                CircularProgressIndicator(
                    color = DanGamesTheme.colors.accent
                )

            is AsyncImagePainter.State.Error ->
                Text(
                    text = "Не удалось загрузить изображение",
                    textAlign = TextAlign.Center
                )

            else ->
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (painterState is AsyncImagePainter.State.Success && elevation > 0.dp)
                                Modifier.shadow(elevation)
                            else Modifier
                        ),
                    contentScale = ContentScale.Crop
                )
        }
    }
}