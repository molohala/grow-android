package com.molohala.infinity

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class ButtonState { Idle, Hold }

@Composable
fun InfinityButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    onClick: () -> Unit
) {
    val isEnabled = enabled && !isLoading

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val color = Color(0xFF2279FF)
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color.copy(alpha = 1f)
        } else {
            color.copy(alpha = 0.64f)
        },
        label = "",
    )
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
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
            contentColor = Color(0xFFFFFFFF),
            disabledContainerColor = animColor,
            disabledContentColor = Color(0xFFFFFFFF),
        ),
        shape = shape,
        enabled = isEnabled,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    InfinityButton(text = "계속하기") {

    }
}