package com.bestswlkh0310.designsystem.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.shadow.ShadowType
import com.bestswlkh0310.designsystem.foundation.shadow.growShadow

@Composable
fun Modifier.applyCardView() = this
    .clip(RoundedCornerShape(12.dp))
    .growShadow(com.bestswlkh0310.designsystem.foundation.shadow.ShadowType.ElevationBlack1)
    .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
