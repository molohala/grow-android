package com.molohala.grow.designsystem.legacy.statcell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.legacy.shimmer.shimmerEffect
import com.molohala.grow.designsystem.extension.applyCardView

@Composable
fun InfinityStatCellShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .applyCardView()
            .padding(4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
               modifier = Modifier
                   .width(100.dp)
                   .height(30.dp)
                   .background(shimmerEffect(), RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(60.dp)
                .background(shimmerEffect(), RoundedCornerShape(4.dp))
        )
    }
}

@Preview
@Composable
private fun StatPreview() {
    InfinityStatCellShimmer()
}