package com.molohala.grow.designsystem.component.button

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.designsystem.extension.ButtonState
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    @DrawableRes leftIcon: Int? = R.drawable.ic_radio,
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
        contentColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Color.Transparent,
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
        colors = colors,
        shape = shape,
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
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
            val primary = if (isSelected) {
                GrowTheme.colorScheme.radioButtonPrimary
            } else {
                GrowTheme.colorScheme.radioButtonSecondary
            }
            val secondary = if (isSelected) {
                GrowTheme.colorScheme.radioButtonSecondary
            } else {
                GrowTheme.colorScheme.radioButtonSecondaryDisabled
            }
            leftIcon?.let {
                GrowIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = it,
                    color = primary
                )
            }
            Text(
                text = text,
                style = GrowTheme.typography.bodyRegular,
                color = secondary
            )
            rightIcon?.let {
                GrowIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = it,
                    color = primary
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
                .padding(10.dp)
                .background(GrowTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowRadioButton(text = "Server", isSelected = true) {

            }
            GrowRadioButton(text = "Web", isSelected = false) {

            }
        }
    }
}