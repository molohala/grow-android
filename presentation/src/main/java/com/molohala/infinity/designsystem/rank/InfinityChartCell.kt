package com.molohala.infinity.designsystem.rank

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.chart.InfinityChart
import com.molohala.infinity.chart.pointsData
import com.molohala.infinity.designsystem.extension.bounceClick
import com.molohala.infinity.icon.IconRightArrow

@Composable
fun InfinityChartCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "이번주에 한 커밋",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Text(
                    text = "116",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconRightArrow()
        }
        InfinityChart(
            modifier = Modifier
                .height(200.dp),
            points = pointsData
        )
    }
}