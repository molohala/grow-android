package com.molohala.infinity.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfinityTextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = RoundedCornerShape(12.dp),
    placeholder: String = "",
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.LightGray,
        focusedContainerColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedContainerColor = Color.White,
        unfocusedTextColor = Color.LightGray,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledTextColor = Color.LightGray,
        disabledContainerColor = Color.White,
    ),
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = shape,
            )
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .border(
                width = (1.5).dp,
                color = Color.LightGray.copy(alpha = 0.5f),
                shape = shape,
            ),
        value = value,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(Color.LightGray),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.LightGray,
                    )
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 16.dp,
                    end = 16.dp
                ),
                shape = shape,
                enabled = enabled,
                colors = colors,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                singleLine = false,
                visualTransformation = VisualTransformation.None,
            )
        },
    )
}

@Preview
@Composable
fun TextFieldPreview() {
    Column {
        InfinityTextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = "",
            onValueChange = {},
            placeholder = "아이디를 입력"
        )
        InfinityTextField(value = "asdasdasd", onValueChange = {}, placeholder = "아이디를 입력")
    }
}