package com.molohala.grow.designsystem.specific.commnuity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.common.util.timeAgo
import com.molohala.grow.data.community.response.CommunityResponse
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.component.button.GrowLikeButton
import com.molohala.grow.designsystem.component.divider.GrowDivider
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews


@Composable
fun GrowCommunityCell(
    modifier: Modifier = Modifier,
    community: CommunityResponse,
    onAppear: () -> Unit = {},
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
            .applyCardView()
            .background(GrowTheme.colorScheme.background)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GrowAvatar(type = AvatarType.Medium)
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = content.writerName,
                    style = GrowTheme.typography.bodyBold,
                    color = GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = content.createdAt.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = GrowTheme.colorScheme.textAlt
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            GrowIcon(
                modifier = Modifier.bounceClick(onClick = {

                }),
                id = R.drawable.ic_detail_vertical,
                color = GrowTheme.colorScheme.textAlt
            )
        }
        Text(
            text = content.content,
            style = GrowTheme.typography.bodyRegular,
            overflow = TextOverflow.Ellipsis,
            maxLines = 5,
            color = GrowTheme.colorScheme.textNormal
        )
        GrowLikeButton(
            like = community.community.like,
            enabled = community.community.liked
        ) {

        }
        recentComment?.let {
            GrowDivider()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.name,
                    style = GrowTheme.typography.labelBold,
                    color = GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = it.content,
                    style = GrowTheme.typography.labelRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = it.createdAt.timeAgo,
                    style = GrowTheme.typography.labelMedium,
                    color = GrowTheme.colorScheme.textAlt
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.backgroundAlt)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowCommunityCell(community = CommunityResponse.dummy()) {

            }
            GrowCommunityCell(community = CommunityResponse.dummy(recentComment = null)) {

            }
        }
    }
}