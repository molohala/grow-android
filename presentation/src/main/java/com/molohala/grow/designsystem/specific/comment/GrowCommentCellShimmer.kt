package com.molohala.grow.designsystem.specific.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shimmer.RowShimmer
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowCommentCellShimmer() {
    Row(
        modifier = Modifier
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        GrowAvatarShimmer(type = AvatarType.Large)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RowShimmer(width = 30.dp)
            RowShimmer(width = 100.dp)
        }
    }
}

@Composable
@GrowPreviews
fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
        ) {
            GrowCommentCellShimmer()
        }
    }
}