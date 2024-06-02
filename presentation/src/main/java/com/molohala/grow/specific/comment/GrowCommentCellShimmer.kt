package com.molohala.grow.specific.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatarShimmer
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.shimmer.RowShimmer
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews

@Composable
fun GrowCommentCellShimmer() {
    Row(
        modifier = Modifier
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        MyAvatarShimmer(type = AvatarType.Large)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RowShimmer(width = 30.dp)
            RowShimmer(width = 100.dp)
        }
    }
}

@Composable
@MyPreviews
fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.background)
        ) {
            GrowCommentCellShimmer()
        }
    }
}