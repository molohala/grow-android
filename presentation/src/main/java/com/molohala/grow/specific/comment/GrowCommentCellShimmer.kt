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
import com.bestswlkh0310.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer

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
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 30.dp)
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 100.dp)
        }
    }
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
        ) {
            GrowCommentCellShimmer()
        }
    }
}