package com.molohala.grow.specific.comment

import android.view.Menu
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
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatar
import com.bestswlkh0310.mydesignsystem.component.menu.MenuType
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenu
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenuData
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews
import com.bestswlkh0310.mydesignsystem.foundation.util.timeAgo
import com.molohala.grow.specific.text.LinkifyText

@Composable
fun GrowCommentCell(
    comment: CommentResponse,
    profileId: Int,
    onReport: () -> Unit,
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
            MyAvatar(type = AvatarType.Large)
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = comment.name,
                        style = MyTheme.typography.bodyBold,
                        color = MyTheme.colorScheme.textNormal
                    )
                    Text(
                        text = comment.createdAt.timeAgo,
                        style = MyTheme.typography.labelMedium,
                        color = MyTheme.colorScheme.textAlt
                    )
                }
                SelectionContainer {
                    LinkifyText(
                        text = comment.content,
                        style = MyTheme.typography.bodyRegular,
                        color = MyTheme.colorScheme.textNormal
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        val me = profileId == comment.memberId
        Column {
            MyIcon(
                modifier = Modifier
                    .size(24.dp)
                    .bounceClick(onClick = {
                        isMenuExpanded = true
                    }),
                id = R.drawable.ic_detail_vertical,
                color = MyTheme.colorScheme.textAlt
            )
            MyMenu(
                expanded = isMenuExpanded,
                menuList = if (me) listOf(
                    MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport),
                    MyMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                ) else listOf(
                    MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport)
                ),
                onDismissRequest = { isMenuExpanded = false }
            )
        }
    }
}

@Composable
@MyPreviews
private fun CommentCellPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowCommentCell(
                comment = CommentResponse.dummy(),
                profileId = 1,
                onReport = {}
            ) {}
        }
    }
}