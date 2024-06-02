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
import com.bestswlkh0310.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer

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
            com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 100.dp)
        }
        Spacer(modifier = Modifier.weight(1f))
        com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer(width = 40.dp)
    }
}

@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
@Composable
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Box(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
                .padding(10.dp),
        ) {
            GrowRankCellShimmer()
        }
    }
}