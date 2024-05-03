package com.molohala.grow.designsystem.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = GrowTheme.typography.bodyMedium,
    shape: Shape = RoundedCornerShape(12.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = GrowTheme.colorScheme.textNormal,
        focusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        unfocusedTextColor = GrowTheme.colorScheme.textNormal,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledTextColor = GrowTheme.colorScheme.textFieldTextDisabled,
        disabledContainerColor = Color.Transparent,
    ),
) {
    var isFocused by remember { mutableStateOf(false) }
    val animBorderColor by animateColorAsState(
        targetValue = if (isFocused) GrowTheme.colorScheme.textFieldPrimary else GrowTheme.colorScheme.textFieldSecondary,
        label = "",
    )

    BasicTextField(
        value = value,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = shape,
            )
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .border(
                width = 1.dp,
                color = animBorderColor,
                shape = shape,
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = textStyle,
        singleLine = singleLine,
        cursorBrush = SolidColor(GrowTheme.colorScheme.textFieldPrimary),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = hint,
                        style = textStyle,
                        color = if (enabled) GrowTheme.colorScheme.textAlt else GrowTheme.colorScheme.textFieldTextDisabled,
                    )
                },
                label = null,
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        GrowIcon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = { onValueChange("") }),
                            id = R.drawable.ic_close_fill,
                            color = GrowTheme.colorScheme.textAlt
                        )
                    }
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 12.dp,
                    end = 8.dp,
                ),
                shape = shape,
                enabled = enabled,
                colors = colors,
                interactionSource = remember { MutableInteractionSource() },
                singleLine = false,
                visualTransformation = VisualTransformation.None,
            )
        },
    )
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            var value by remember { mutableStateOf("야삐") }
            GrowTextField(
                value = value,
                hint = "야삐",
                onValueChange = {
                    value = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            GrowTextField(
                value = value,
                hint = "야삐",
                onValueChange = {
                    value = it
                },
                enabled = false,
            )
        }
    }
}