package com.molohala.infinity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String,
    onClickBackButton: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        onClickBackButton?.let {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .bounceClick(onClick = it)
                    .graphicsLayer(rotationY = 180f),
                painter = painterResource(id = R.drawable.ic_expand_right),
                contentDescription = null,
                tint = Color.Black
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp),
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}