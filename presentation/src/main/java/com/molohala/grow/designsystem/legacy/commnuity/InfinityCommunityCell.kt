package com.molohala.grow.designsystem.commnuity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.common.util.timeAgo
import com.molohala.grow.data.community.response.CommunityResponse
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick


@Composable
fun InfinityCommunityCell(
    modifier: Modifier = Modifier,
    community: CommunityResponse,
    onAppear: () -> Unit,
    onClick: () -> Unit
) {
    val content = community.community
    val recentComment = community.recentComment

    LaunchedEffect(Unit) {
        onAppear()
    }

    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .size(36.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = content.writerName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = content.createdAt.timeAgo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .bounceClick(onClick = {

                    }),
                painter = painterResource(id = R.drawable.ic_detail_vertical),
                contentDescription = "more",
                tint = Color.LightGray
            )
        }
        Column {
            Text(
                text = content.content,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .bounceClick(onClick = {

                        }),
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    tint = Color.Red
                )
                Text(
                    text = content.like.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            recentComment?.let {
                Divider(color = GrowColor.gray100)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = it.content,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = it.createdAt.timeAgo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}