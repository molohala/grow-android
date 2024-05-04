package com.molohala.grow.designsystem.foundation.shimmer

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.molohala.grow.designsystem.foundation.color.GrowPallete

@Composable
fun shimmerEffect(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val darkTheme = isSystemInDarkTheme()
        // Colors for the shimmer effect
        val color = GrowPallete.Neutral50
        val added = if (darkTheme) 0.5f else 0f
        val shimmerColors = listOf(
            color.copy(alpha = 0.25f + added),
            color.copy(alpha = 0.15f + added),
            color.copy(alpha = 0.25f + added),
        )

        // Start the animation transition
        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(3000),
            ),
            label = "",
        )

        // Return a linear gradient brush
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnimation.value - 200f, y = 0f),
            end = Offset(x = translateAnimation.value, y = 0f),
        )
    } else {
        // If shimmer is turned off, return a transparent brush
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero,
        )
    }
}