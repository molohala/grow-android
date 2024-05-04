package com.molohala.grow.designsystem.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shadow.ShadowType
import com.molohala.grow.designsystem.foundation.shadow.growShadow

@Composable
fun Modifier.applyCardView() = this
    .clip(RoundedCornerShape(12.dp))
    .growShadow(ShadowType.ElevationBlack1)
    .background(GrowTheme.colorScheme.background)
