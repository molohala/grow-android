package com.molohala.infinity.designsystem.github

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
import com.molohala.infinity.R
import com.molohala.infinity.data.rank.response.RankResponse
import com.molohala.infinity.designsystem.extension.bounceClick
import com.molohala.infinity.extension.applyCardView

@Composable
fun InfinityGithubRankCell(
    modifier: Modifier = Modifier,
    isCard: Boolean = false,
    githubRank: RankResponse,
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
            text = "${githubRank.rank}",
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
            val medal = when (githubRank.rank) {
                1 -> R.drawable.first_medal
                2 -> R.drawable.second_medal
                3 -> R.drawable.third_medal
                else -> null
            }
            if (githubRank.rank in 1..<4 && medal != null) {
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
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = githubRank.memberName,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Text(
                text = githubRank.socialId,
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
            text = "${githubRank.count} 커밋",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}