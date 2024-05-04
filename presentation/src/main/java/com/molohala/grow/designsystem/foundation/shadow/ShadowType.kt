package com.molohala.grow.designsystem.foundation.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.shadow
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

sealed class ShadowType(
    val y: Int,
    val blur: Int,
    val alpha: Float
) {
    data object ElevationBlack1 : ShadowType(y = 3, blur = 10, alpha = 0.02f)
    data object ElevationBlack2 : ShadowType(y = 4, blur = 12, alpha = 0.04f)
    data object ElevationBlack3 : ShadowType(y = 6, blur = 18, alpha = 0.06f)
}

@Composable
fun Modifier.growShadow(type: ShadowType): Modifier {

    val color = when (type) {
        ShadowType.ElevationBlack1 -> GrowTheme.colorScheme.elevationBlack1
        ShadowType.ElevationBlack2 -> GrowTheme.colorScheme.elevationBlack2
        ShadowType.ElevationBlack3 -> GrowTheme.colorScheme.elevationBlack2
    }

    return this.shadow(
        offsetY = type.y.dp, color = color, blur = type.blur.dp, alpha = type.alpha
    )
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Surface(
            color = GrowTheme.colorScheme.background
        ) {
            Row(
                modifier = Modifier.padding(30.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .growShadow(type = ShadowType.ElevationBlack1)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GrowTheme.colorScheme.background)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .growShadow(type = ShadowType.ElevationBlack2)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GrowTheme.colorScheme.background)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .growShadow(type = ShadowType.ElevationBlack3)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GrowTheme.colorScheme.background)
                )
            }
        }
    }
}