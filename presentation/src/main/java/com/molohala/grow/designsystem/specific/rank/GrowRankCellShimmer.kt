package com.molohala.grow.designsystem.specific.rank

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
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews
import com.molohala.grow.designsystem.foundation.shimmer.ShimmerRowBox

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

            GrowAvatarShimmer(type = AvatarType.Large)
            ShimmerRowBox(width = 100.dp)
        }
        Spacer(modifier = Modifier.weight(1f))
        ShimmerRowBox(width = 40.dp)
    }
}

@GrowPreviews
@Composable
private fun Preview() {
    GrowTheme {
        Box(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp),
        ) {
            GrowRankCellShimmer()
        }
    }
}