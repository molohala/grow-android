package com.molohala.infinity.designsystem.commnuity

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.molohala.infinity.R
import com.molohala.infinity.designsystem.color.InfinityColor
import com.molohala.infinity.common.util.timeAgo
import com.molohala.infinity.data.community.response.CommunityResponse
import com.molohala.infinity.designsystem.extension.bounceClick
import com.molohala.infinity.extension.applyCardView


@Composable
fun InfinityCommunityCell(
    modifier: Modifier = Modifier,
    community: CommunityResponse,
    onAppear: () -> Unit,
    onClick: () -> Unit
) {
    val content = community.community
    val recentComment = community.recentComment
    val minimumLineLength = 2   //Change this to your desired value

    //Adding States
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else minimumLineLength

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
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "more",
                tint = Color.LightGray
            )
        }
        Column {
            Text(
                text = content.content,
                style = MaterialTheme.typography.bodyLarge,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.lineCount > minimumLineLength - 1) {
                        if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) {
                            showReadMoreButtonState = true
                        }
                    }
                },
                overflow = TextOverflow.Ellipsis
            )
            if (expandedState) {
                Text(
                    text = "더보기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row {
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
                Divider(color = InfinityColor.gray100)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
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