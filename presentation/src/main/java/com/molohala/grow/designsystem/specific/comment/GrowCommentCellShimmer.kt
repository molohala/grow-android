package com.molohala.grow.designsystem.specific.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.foundation.shimmer.shimmerEffect

@Composable
fun GrowCommentCellShimmer() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(shimmerEffect(), RoundedCornerShape(4.dp))
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(30.dp)
                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(100.dp)
                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
@Preview
fun Preview() {
    GrowCommentCellShimmer()
}