package com.molohala.grow.designsystem.specific.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowChartCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "77",
                    style = GrowTheme.typography.title1B,
                    color = GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = "이번주에 한 커밋",
                    style = GrowTheme.typography.labelMedium,
                    color = GrowTheme.colorScheme.textDarken
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        GrowChart(
            modifier = Modifier
                .height(200.dp),
            points = pointsData,
            background = GrowTheme.colorScheme.background
        )
    }
}

@GrowPreviews
@Composable
private fun Preview() {
    GrowTheme {
        GrowChartCell {

        }
    }
}