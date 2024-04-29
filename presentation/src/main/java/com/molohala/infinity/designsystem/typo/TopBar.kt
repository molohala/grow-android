package com.molohala.infinity.designsystem.typo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.infinity.R
import com.molohala.infinity.designsystem.color.InfinityColor
import com.molohala.infinity.extension.bounceClick

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    text: String,
    onClickBackButton: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .background(backgroundColor)
                .padding(start = 16.dp),
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
        content()
    }
}

@Composable
@Preview
fun TopBarPreview() {
    TopBar(
        modifier = Modifier.background(InfinityColor.background),
        text = "히히"
    ) {
        Text(text = "히히")
    }
}