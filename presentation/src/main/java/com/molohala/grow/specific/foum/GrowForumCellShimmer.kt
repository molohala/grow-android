package com.molohala.grow.specific.foum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.component.avatar.AvatarType
import com.bestswlkh0310.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer


@Composable
fun GrowForumCellShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .applyCardView()
            .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GrowAvatarShimmer(type = AvatarType.Medium)
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 50.dp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 100.dp)
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 240.dp)
        }
    }
}
@Preview
@Composable
private fun ShimmerPriview() {
    GrowForumCellShimmer()
}