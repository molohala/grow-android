package com.molohala.grow.designsystem.specific.statcell

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
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowStatCell(
    modifier: Modifier = Modifier,
    label: String,
    type: GrowStatType,
    onClick: () -> Unit
) {

    val iconColor = when (type) {
        is GrowStatType.Baekjoon -> GrowTheme.colorScheme.baekjoon
        is GrowStatType.Github -> GrowTheme.colorScheme.github
    }

    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
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
                style = GrowTheme.typography.title1B,
                color = GrowTheme.colorScheme.textNormal
            )
            Spacer(modifier = Modifier.weight(1f))
            GrowIcon(
                modifier = Modifier
                    .size(32.dp),
                id = type.icon,
                color = iconColor
            )
        }
        Text(
            text = label,
            style = GrowTheme.typography.labelMedium,
            color = GrowTheme.colorScheme.textDarken
        )
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Row(
            modifier = Modifier
                .background(GrowTheme.colorScheme.backgroundAlt)
                .padding(10.dp)
        ) {
            GrowStatCell(label = "오늘 한 커밋 개수", type = GrowStatType.Baekjoon(solved = 10)) {

            }
        }
    }
}