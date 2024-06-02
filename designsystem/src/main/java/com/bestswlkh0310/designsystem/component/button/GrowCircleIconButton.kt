package com.bestswlkh0310.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.R
import com.bestswlkh0310.designsystem.extension.ButtonState
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.iconography.GrowIcon
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews


@Composable
fun GrowCircleIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(14.dp),
    onClick: () -> Unit,
) {
    val isEnabled = enabled && !isLoading

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val color =
        if (enabled) GrowTheme.colorScheme.buttonPrimary
        else GrowTheme.colorScheme.buttonPrimaryDisabled
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color
        } else {
            color
        },
        label = "",
    )

    Box(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = modifier
                .then(modifier)
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
            colors = ButtonDefaults.buttonColors(
                containerColor = animColor,
                contentColor = GrowTheme.colorScheme.textNormal,
                disabledContainerColor = animColor,
                disabledContentColor = GrowTheme.colorScheme.buttonTextDisabled,
            ),
            enabled = isEnabled,
            shape = CircleShape,
            contentPadding = contentPadding,
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
            val textColor = if (enabled) {
                GrowTheme.colorScheme.buttonText
            } else {
                GrowTheme.colorScheme.buttonTextDisabled
            }
            GrowIcon(
                modifier = Modifier
                    .size(28.dp),
                id = icon,
                color = textColor
            )
//            }
        }
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowCircleIconButton(icon = R.drawable.ic_write) {

            }
            GrowCircleIconButton(icon = R.drawable.ic_write, enabled = false) {

            }
        }
    }
}
