package com.molohala.grow.specific.text

import androidx.compose.foundation.background
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews

@Composable
fun Headline(
    modifier: Modifier = Modifier,
    text: String,
    content: (@Composable () -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = MyTheme.typography.headline1B,
            color = MyTheme.colorScheme.textNormal
        )
        Spacer(modifier = Modifier.weight(1f))
        content?.invoke()
    }
}

@MyPreviews
@Composable
private fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.background)
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