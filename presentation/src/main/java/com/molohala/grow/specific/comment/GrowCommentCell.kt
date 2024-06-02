package com.molohala.grow.specific.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.data.comment.response.CommentResponse
import com.bestswlkh0310.designsystem.component.avatar.AvatarType
import com.bestswlkh0310.designsystem.component.avatar.GrowAvatar
import com.bestswlkh0310.designsystem.extension.bounceClick
import com.bestswlkh0310.designsystem.foundation.util.timeAgo
import com.bestswlkh0310.designsystem.R
import com.molohala.grow.designsystem.component.menu.GrowMenu
import com.molohala.grow.designsystem.component.menu.GrowMenuData
import com.molohala.grow.designsystem.component.menu.MenuType

@Composable
fun GrowCommentCell(
    comment: CommentResponse,
    profileId: Int,
    onRemove: () -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

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
                        style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.bodyBold,
                        color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
                    )
                    Text(
                        text = comment.createdAt.timeAgo,
                        style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.labelMedium,
                        color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textAlt
                    )
                }
                SelectionContainer {
                    Text(
                        text = comment.content,
                        style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.bodyRegular,
                        color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (profileId == comment.memberId) {
            Column {
                com.bestswlkh0310.designsystem.foundation.iconography.GrowIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .bounceClick(onClick = {
                            isMenuExpanded = true
                        }),
                    id = R.drawable.ic_detail_vertical,
                    color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textAlt
                )
                GrowMenu(
                    expanded = isMenuExpanded,
                    menuList = listOf(
                        GrowMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                    ),
                    onDismissRequest = { isMenuExpanded = false }
                )
            }
        }
    }
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun CommentCellPreview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowCommentCell(
                comment = CommentResponse.dummy(),
                profileId = 1
            ) {}
        }
    }
}