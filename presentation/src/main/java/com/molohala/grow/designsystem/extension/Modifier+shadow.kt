package com.molohala.grow.designsystem.extension

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    color: Color,
    alpha: Float = 0.2f,
    blur: Dp = 0.dp,
    offsetY: Dp = 0.dp
) = drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        frameworkPaint.color = color.copy(alpha = alpha).toArgb()

        if (blur != 0.dp) {
            frameworkPaint.maskFilter = BlurMaskFilter(
                blur.toPx(),
                BlurMaskFilter.Blur.NORMAL,
            )
        }

        canvas.drawRoundRect(
            left = 0f,
            top = offsetY.toPx(),
            right = size.width,
            bottom = size.height,
            radiusX = 0f,
            radiusY = 0f,
            paint = paint,
        )
    }
}
