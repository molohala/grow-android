package com.molohala.infinity.typo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.infinity.R
import com.molohala.infinity.extension.bounceClick

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Row {
        Text(
            modifier = Modifier
                .padding(start = 4.dp),
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        SubTitle(text = "안녕하세요")
    }
}