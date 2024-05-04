package com.molohala.grow.designsystem.specific.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.common.util.timeAgo
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowCommentCell(
    comment: CommentResponse,
    onClickRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            GrowAvatar(type = AvatarType.Large)
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = comment.name,
                        style = GrowTheme.typography.bodyBold,
                        color = GrowTheme.colorScheme.textNormal
                    )
                    Text(
                        text = comment.createdAt.timeAgo,
                        style = GrowTheme.typography.labelMedium,
                        color = GrowTheme.colorScheme.textAlt
                    )
                }
                Text(
                    text = comment.content,
                    style = GrowTheme.typography.bodyRegular,
                    color = GrowTheme.colorScheme.textNormal
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        GrowIcon(
            modifier = Modifier
                .size(24.dp),
            id = R.drawable.ic_detail_vertical,
            color = GrowTheme.colorScheme.textAlt
        )
    }
}

@Composable
@GrowPreviews
private fun CommentCellPreview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowCommentCell(comment = CommentResponse.dummy()) {}
        }
    }
}