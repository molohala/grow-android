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
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatarShimmer
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.shimmer.RowShimmer

@Composable
fun GrowForumCellShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .applyCardView()
            .background(MyTheme.colorScheme.background)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MyAvatarShimmer(type = AvatarType.Medium)
            RowShimmer(width = 50.dp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RowShimmer(width = 100.dp)
            RowShimmer(width = 240.dp)
        }
    }
}
@Preview
@Composable
private fun ShimmerPriview() {
    GrowForumCellShimmer()
}