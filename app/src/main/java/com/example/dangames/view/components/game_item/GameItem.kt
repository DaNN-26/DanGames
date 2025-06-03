package com.example.dangames.view.components.game_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dangames.design.theme.DanGamesTheme
import com.example.dangames.network.domain.model.Model
import com.example.dangames.view.components.image.AsyncImageWithProgress
import com.example.dangames.view.components.rating.Rating

@Composable
fun GameItem(
    onClick: () -> Unit,
    game: Model.Game,
    modifier: Modifier = Modifier,
    needToRestart: Boolean = false
) {
    Card(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = DanGamesTheme.colors.singleAltColor
        )
    ) {
        AsyncImageWithProgress(
            imagePath = game.backgroundImage ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f),
            elevation = 4.dp,
            restart = { key(needToRestart) { if (needToRestart) it.restart() } }
        )
        Spacer(Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Rating(rating = game.metacritic ?: -1)
            Text(
                text = game.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DanGamesTheme.colors.opposite,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
            game.genres.apply {
                if (isNotEmpty())
                    Text(
                        text = game.genres.joinToString(", ") { it.name },
                        color = DanGamesTheme.colors.hint
                    )
            }
        }
    }
}