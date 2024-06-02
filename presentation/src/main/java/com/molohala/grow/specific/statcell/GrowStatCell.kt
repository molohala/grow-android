package com.molohala.grow.specific.statcell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews

@Composable
fun GrowStatCell(
    modifier: Modifier = Modifier,
    label: String,
    type: GrowStatType,
    socialId: String? = null,
    onClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val iconColor = when (type) {
        is GrowStatType.Baekjoon -> MyTheme.colorScheme.baekjoon
        is GrowStatType.Github -> MyTheme.colorScheme.github
    }

    Column(
        modifier = modifier
            .bounceClick(onClick = {
                socialId?.let {
                    val uri = when (type) {
                        is GrowStatType.Baekjoon -> "https://acmicpc.net/user/${it}"
                        is GrowStatType.Github -> "http://github.com/${it}"
                    }
                    uriHandler.openUri(uri)
                }
                onClick()
            })
            .applyCardView()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val number = when (type) {
                is GrowStatType.Baekjoon -> type.solved
                is GrowStatType.Github -> type.commit
            }
            Text(
                text = number?.toString() ?: "??",
                style = MyTheme.typography.title1B,
                color = MyTheme.colorScheme.textNormal
            )
            Spacer(modifier = Modifier.weight(1f))
            MyIcon(
                modifier = Modifier
                    .size(32.dp),
                id = type.icon,
                color = iconColor
            )
        }
        Text(
            text = label,
            style = MyTheme.typography.labelMedium,
            color = MyTheme.colorScheme.textDarken
        )
    }
}

@Composable
@MyPreviews
private fun Preview() {
    MyTheme {
        Row(
            modifier = Modifier
                .background(MyTheme.colorScheme.backgroundAlt)
                .padding(10.dp)
        ) {
            GrowStatCell(label = "오늘 한 커밋 개수", type = GrowStatType.Baekjoon(solved = 10)) {

            }
        }
    }
}