package com.example.dangames.view.components.game_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dangames.design.theme.DanGamesTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun GameItemShimmer() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = DanGamesTheme.colors.singleAltColor
        )
    ) {
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth()
                .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f))
                .aspectRatio(1.4f),
        )
        Spacer(Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .border(
                        width = 1.dp,
                        color = DanGamesTheme.colors.hint.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {
                Text(
                    text = "00",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                    color = Color.Transparent
                )
            }
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f))
            )
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .background(DanGamesTheme.colors.hint.copy(alpha = 0.5f))
            )
        }
    }
}