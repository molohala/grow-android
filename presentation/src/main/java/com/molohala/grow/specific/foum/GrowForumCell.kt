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
import com.molohala.grow.data.forum.response.ForumResponse
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatar
import com.bestswlkh0310.mydesignsystem.component.menu.MenuType
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenu
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.util.timeAgo
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.button.MyLikeButton
import com.bestswlkh0310.mydesignsystem.component.divider.MyDivider
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenuData
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews

@Composable
fun GrowForumCell(
    modifier: Modifier = Modifier,
    forum: ForumResponse,
    profileId: Int,
    onAppear: () -> Unit = {},
    onClickLike: () -> Unit,
    onRemove: () -> Unit,
    onEdit: () -> Unit,
    onReport: () -> Unit,
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
            MyAvatar(type = AvatarType.Medium)
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = content.writerName,
                    style = MyTheme.typography.bodyBold,
                    color = MyTheme.colorScheme.textNormal
                )
                Text(
                    text = content.createdAt.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = MyTheme.colorScheme.textAlt
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            val me = profileId == forum.forum.writerId
            Column {
                MyIcon(
                    modifier = Modifier
                        .bounceClick(onClick = {
                            isMenuExpanded = true
                        }),
                    id = R.drawable.ic_detail_vertical,
                    color = MyTheme.colorScheme.textAlt
                )
                MyMenu(
                    expanded = isMenuExpanded,
                    menuList = if (me) listOf(
                        MyMenuData("수정하기", onClick = onEdit),
                        MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport),
                        MyMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                    ) else listOf(
                        MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport)
                    ),
                    onDismissRequest = { isMenuExpanded = false }
                )
            }
        }
        SelectionContainer {
            Text(
                text = content.content,
                style = MyTheme.typography.bodyRegular,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
                color = MyTheme.colorScheme.textNormal
            )
        }
        MyLikeButton(like = forum.forum.like, enabled = forum.forum.liked, onClick = onClickLike)
        recentComment?.let {
            MyDivider()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.name,
                    style = MyTheme.typography.labelBold,
                    color = MyTheme.colorScheme.textNormal
                )
                SelectionContainer {
                    Text(
                        modifier = Modifier
                            .widthIn(0.dp, 180.dp),
                        text = it.content,
                        style = MyTheme.typography.labelRegular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MyTheme.colorScheme.textNormal
                    )
                }
                Text(
                    modifier = Modifier,
                    text = it.createdAt.timeAgo,
                    style = MyTheme.typography.labelMedium,
                    color = MyTheme.colorScheme.textAlt
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@MyPreviews
private fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.backgroundAlt)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowForumCell(
                forum = ForumResponse.dummy(),
                onRemove = {},
                onEdit = {

                },
                onClickLike = {},
                onReport = {},
                profileId = 1
            ) {

            }
            GrowForumCell(
                forum = ForumResponse.dummy(recentComment = null),
                onRemove = {},
                onEdit = {},
                onClickLike = {},
                onReport = {},
                profileId = 1
            ) {

            }
        }
    }
}