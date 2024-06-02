package com.molohala.grow.specific.statcell

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
import com.bestswlkh0310.designsystem.extension.applyCardView
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews

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
            RowShimmer(width = 40.dp)
            Spacer(modifier = Modifier.weight(1f))
        }
        RowShimmer(width = 100.dp)
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