package com.molohala.grow.designsystem.specific.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun Headline(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        style = GrowTheme.typography.headline1B,
        color = GrowTheme.colorScheme.textNormal
    )
}

@GrowPreviews
@Composable
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Headline(text = "오늘의 Github Top 3")

            Column {
                Headline(text = "iOS 개발자")
                Headline(text = "이강현님 환영합니다")
            }
        }
    }
}