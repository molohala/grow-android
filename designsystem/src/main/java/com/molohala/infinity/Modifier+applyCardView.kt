package com.molohala.infinity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.applyCardView(): Modifier = this
    .clip(RoundedCornerShape(10.dp))
    .drawColoredShadow(
        color = Color.Black,
        alpha = 0.04f,
        offsetY = 3.dp,
        borderRadius = 8.dp,
        shadowRadius = 8.dp
    )
    .background(Color.White)
    .padding(all = 12.dp)
