package com.molohala.grow.specific.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun String.annotatedString() = remember(this) {
    buildAnnotatedString {
        append(this@annotatedString)
        val regex = "(https?://[\\w-]+(\\.[\\w-]+)+(:\\d+)?(/[\\w- ./?%&=]*)?)".toRegex()
        regex.findAll(this@annotatedString).forEach { result ->
            val start = result.range.first
            val end = result.range.last + 1
            addStyle(
                style = SpanStyle(
                    color = Color(0xFF0085FF),
                    textDecoration = TextDecoration.Underline
                ),
                start = start,
                end = end
            )
            addStringAnnotation(
                tag = "URL",
                annotation = result.value,
                start = start,
                end = end
            )
        }
    }
}