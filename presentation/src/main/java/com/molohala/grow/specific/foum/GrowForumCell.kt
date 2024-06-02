package com.molohala.grow.specific.foum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.R
import com.molohala.grow.data.forum.response.ForumResponse
import com.bestswlkh0310.designsystem.component.avatar.AvatarType
import com.bestswlkh0310.designsystem.component.avatar.GrowAvatar
import com.bestswlkh0310.designsystem.component.button.GrowLikeButton
import com.bestswlkh0310.designsystem.extension.applyCardView
import com.bestswlkh0310.designsystem.extension.bounceClick
import com.bestswlkh0310.designsystem.foundation.util.timeAgo
import com.molohala.grow.designsystem.component.divider.GrowDivider
import com.molohala.grow.designsystem.component.menu.GrowMenu
import com.molohala.grow.designsystem.component.menu.GrowMenuData
import com.molohala.grow.designsystem.component.menu.MenuType

@Composable
fun GrowForumCell(
    modifier: Modifier = Modifier,
    forum: ForumResponse,
    profileId: Int,
    onAppear: () -> Unit = {},
    onClickLike: () -> Unit,
    onRemove: () -> Unit,
    onEdit: () -> Unit,
    onClick: () -> Unit
) {
    val content = forum.forum
    val recentComment = forum.recentComment
    var isMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onAppear()
    }

    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView()
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
                    style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.bodyBold,
                    color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = content.createdAt.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textAlt
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (profileId == forum.forum.writerId) {
                Column {
                    com.bestswlkh0310.designsystem.foundation.iconography.GrowIcon(
                        modifier = Modifier
                            .bounceClick(onClick = {
                                isMenuExpanded = true
                            }),
                        id = R.drawable.ic_detail_vertical,
                        color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textAlt
                    )
                    GrowMenu(
                        expanded = isMenuExpanded,
                        menuList = listOf(
                            GrowMenuData("수정하기", onClick = onEdit),
                            GrowMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                        ),
                        onDismissRequest = { isMenuExpanded = false }
                    )
                }
            }
        }
        SelectionContainer {
            Text(
                text = content.content,
                style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.bodyRegular,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
                color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
            )
        }
        GrowLikeButton(like = forum.forum.like, enabled = forum.forum.liked, onClick = onClickLike)
        recentComment?.let {
            GrowDivider()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.name,
                    style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.labelBold,
                    color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
                )
                SelectionContainer {
                    Text(
                        modifier = Modifier
                            .widthIn(0.dp, 180.dp),
                        text = it.content,
                        style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.labelRegular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal
                    )
                }
                Text(
                    modifier = Modifier,
                    text = it.createdAt.timeAgo,
                    style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.labelMedium,
                    color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textAlt
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.backgroundAlt)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowForumCell(
                forum = ForumResponse.dummy(),
                onRemove = {},
                onEdit = {

                },
                onClickLike = {},
                profileId = 1
            ) {

            }
            GrowForumCell(
                forum = ForumResponse.dummy(recentComment = null),
                onRemove = {},
                onEdit = {},
                onClickLike = {},
                profileId = 1
            ) {

            }
        }
    }
}