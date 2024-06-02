package com.molohala.grow.specific.motivation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.specific.motivation.motivations

@Composable
fun GrowMotivationCell(
    modifier: Modifier = Modifier
) {

    val motivation by remember {
        mutableStateOf(motivations.random())
    }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = motivation,
        style = GrowTheme.typography.headline2R.copy(textAlign = TextAlign.Center),
        color = GrowTheme.colorScheme.textNormal
    )
}
