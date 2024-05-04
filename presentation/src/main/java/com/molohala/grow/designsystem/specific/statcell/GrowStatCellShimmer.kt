package com.molohala.grow.designsystem.specific.statcell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews
import com.molohala.grow.designsystem.foundation.shimmer.ShimmerRowBox

@Composable
fun GrowStatCellShimmer(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .applyCardView()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerRowBox(width = 40.dp)
            Spacer(modifier = Modifier.weight(1f))
        }
        ShimmerRowBox(width = 100.dp)
    }
}

@GrowPreviews
@Composable
private fun StatPreview() {
    GrowTheme {
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            GrowStatCellShimmer()
        }
    }
}