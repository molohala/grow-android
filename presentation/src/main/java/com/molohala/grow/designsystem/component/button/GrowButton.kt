package com.molohala.grow.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.ButtonState
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

sealed class ButtonType(
    val contentPadding: PaddingValues,
    val shape: Shape
) {
    data object Large : ButtonType(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        shape = RoundedCornerShape(10.dp)
    )

    data object Medium : ButtonType(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(8.dp)
    )

    data object Small : ButtonType(
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
        shape = RoundedCornerShape(6.dp)
    )
}

@Composable
fun GrowButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    shape: Shape? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
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

    Button(
        onClick = onClick,
        modifier = modifier
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
        shape = shape?: type.shape,
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
            val textColor = if (enabled) {
                GrowTheme.colorScheme.buttonText
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
            GrowButton(
                text = "시작하기",
                type = ButtonType.Large
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Large,
                isLoading = true
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Large,
                enabled = false
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Medium
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Medium,
                isLoading = true
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Medium,
                enabled = false
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Small
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Small,
                isLoading = true
            ) {

            }
            GrowButton(
                text = "시작하기",
                type = ButtonType.Small,
                enabled = false
            ) {

            }
        }
    }
}