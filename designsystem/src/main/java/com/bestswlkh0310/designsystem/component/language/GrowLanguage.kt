package com.molohala.grow.designsystem.component.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowLanguage(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background),
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Center),
            text = text,
            style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.labelMedium,
            color = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textDarken
        )
    }
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            GrowLanguage(text = "Swift")
            GrowLanguage(text = "Mojo")
        }
    }
}