package com.molohala.grow.designsystem.foundation.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowShimmer(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp = 20.dp
) {
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .background(shimmerEffect(), RoundedCornerShape(4.dp)),
    )
}