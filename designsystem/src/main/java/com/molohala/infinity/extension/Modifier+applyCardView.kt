package com.molohala.infinity.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.applyCardView(): Modifier = composed { this
    .drawColoredShadow(
        color = Color.Black,
        alpha = 0.04f,
        offsetY = 3.dp,
        blur = 8.dp,
    )
    .clip(RoundedCornerShape(10.dp))
    .background(Color.White)
    .padding(all = 12.dp) }
