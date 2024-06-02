package com.bestswlkh0310.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.extension.ButtonState
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.iconography.GrowIcon

@Composable
fun GrowTabButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.Small,
    selected: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    shape: Shape = RoundedCornerShape(8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )

    val colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = GrowTheme.colorScheme.buttonPrimary,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = GrowTheme.colorScheme.buttonTextDisabled,
    )

    val indicatorColor = GrowTheme.colorScheme.tabButtonPrimary

    Button(
        onClick = onClick,
        modifier = modifier
            .drawBehind {
                if (!selected) {
                    return@drawBehind
                }
                val borderSize = 1.dp.toPx()
                val y = size.height - borderSize / 2
                drawLine(
                    color = indicatorColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderSize
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(buttonState) {
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Hold) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        ButtonState.Hold
                    }
                }
            },
        colors = colors,
        shape = shape,
        contentPadding = type.contentPadding,
        interactionSource = interactionSource,
    ) {
//            if (isLoading) {
//                RiveAnimation(
//                    resId = R.raw.loading_dots,
//                    contentDescription = "loading gif",
//                    autoplay = true,
//                    animationName = type.animName,
//                )
//            } else {

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = if (selected) {
                GrowTheme.colorScheme.tabButtonPrimary
            } else {
                GrowTheme.colorScheme.buttonTextDisabled
            }
            leftIcon?.let {
                GrowIcon(
                    modifier = Modifier
                        .size(20.dp),
                    id = it,
                    color = textColor
                )
            }
            Text(
                text = text,
                style = GrowTheme.typography.bodyBold,
                color = textColor
            )
            rightIcon?.let {
                GrowIcon(
                    modifier = Modifier
                        .size(20.dp),
                    id = it,
                    color = textColor
                )
            }
        }
//            }
    }
}
