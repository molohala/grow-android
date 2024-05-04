package com.molohala.grow.designsystem.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = GrowTheme.colorScheme.background,
    onClickBackButton: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(backgroundColor)
            .safeDrawingPadding()
    ) {
        Row(
            modifier = modifier
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                onClickBackButton?.let {
                    GrowIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                            .clickable(onClick = it),
                        id = R.drawable.ic_expand_left,
                        color = GrowTheme.colorScheme.textNormal
                    )
                }
                val textStyle =
                    if (onClickBackButton == null) GrowTheme.typography.title2B
                    else GrowTheme.typography.headline2M
                Text(
                    text = text,
                    style = textStyle,
                    color = GrowTheme.colorScheme.textNormal
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
            ) {
                trailingContent?.let { content ->
                    content()
                }
            }
        }
        content()
    }
}

@Composable
@GrowPreviews
private fun TopBarPreview() {
    GrowTopAppBar(
        text = "히히",
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
        trailingContent = {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "complete")
            }
        }
    ) {
        Text(text = "히히")
    }
}

@Composable
@GrowPreviews
private fun TopBarWithBackButtonPreview() {
    GrowTopAppBar(
        text = "히히",
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
        onClickBackButton = {

        },
    ) {
        Text(text = "히히")
    }
}