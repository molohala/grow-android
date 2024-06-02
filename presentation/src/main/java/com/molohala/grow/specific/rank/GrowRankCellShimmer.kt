package com.molohala.grow.specific.rank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun GrowRankCellShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyAvatarShimmer(type = AvatarType.Large)
            RowShimmer(width = 100.dp)
        }
        Spacer(modifier = Modifier.weight(1f))
        RowShimmer(width = 40.dp)
    }
}

@MyPreviews
@Composable
private fun Preview() {
    MyTheme {
        Box(
            modifier = Modifier
                .background(MyTheme.colorScheme.background)
                .padding(10.dp),
        ) {
            GrowRankCellShimmer()
        }
    }
}