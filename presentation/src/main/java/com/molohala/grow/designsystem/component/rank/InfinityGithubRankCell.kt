package com.molohala.grow.designsystem.component.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molohala.grow.R
import com.molohala.grow.data.rank.response.RankResponse
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.extension.applyCardView

@Composable
fun InfinityGithubRankCell(
    modifier: Modifier = Modifier,
    isCard: Boolean = false,
    rank: RankResponse,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .defaultMinSize(minHeight = 60.dp)
            .then(if (isCard) Modifier.applyCardView() else Modifier),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${rank.rank}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
            )
            val medal = when (rank.rank) {
                1 -> R.drawable.first_medal
                2 -> R.drawable.second_medal
                3 -> R.drawable.third_medal
                else -> null
            } ?: return@Box
            Column {
                Image(
                    modifier = Modifier
                        .size(28.dp),
                    painter = painterResource(id = medal),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = rank.memberName,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Text(
                text = rank.socialId,
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W400
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "${rank.count} 커밋",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}