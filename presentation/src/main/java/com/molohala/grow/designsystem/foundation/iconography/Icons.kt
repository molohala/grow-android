package com.molohala.grow.designsystem.foundation.iconography

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun GrowIcon(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    color: Color
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = id),
        contentDescription = null,
        tint = color
    )
}