package com.molohala.grow.designsystem.component.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews

sealed class DividerType(val size: Dp) {
    data object Thin: DividerType(size = 1.dp)
    data object Thick: DividerType(size = 8.dp)
}

@Composable
fun GrowDivider(
    modifier: Modifier = Modifier,
    thickness: DividerType = DividerType.Thin,
    color: Color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.dividerNormal
) {
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(thickness.size),
    )
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            GrowDivider()
            GrowDivider(thickness = DividerType.Thick)
        }
    }
}