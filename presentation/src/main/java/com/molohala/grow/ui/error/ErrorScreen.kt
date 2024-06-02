package com.molohala.grow.ui.error

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Error...",
            style = MyTheme.typography.headline1B,
            color = MyTheme.colorScheme.textNormal
        )
        Text(
            modifier = Modifier
                .clickable(onClick = {
                    // action
                }),
            text = "그로우 개발 스토리 보러가기",
            style = MyTheme.typography.bodyRegular.copy(textDecoration = TextDecoration.Underline),
            color = MyTheme.colorScheme.textDarken
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}