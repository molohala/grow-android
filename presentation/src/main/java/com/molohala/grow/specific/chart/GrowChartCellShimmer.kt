package com.molohala.grow.specific.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.extension.applyCardView
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.shimmer.RowShimmer
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
import com.molohala.grow.designsystem.specific.chart.GrowChartCell

@Composable
fun GrowChartCellShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
                RowShimmer(
                    width = 70.dp,
                    height = 30.dp
                )
                RowShimmer(width = 100.dp)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@GrowPreviews
@Composable
private fun Preview() {
    GrowTheme {
        GrowChartCell(
            chartInfo = GrowChartInfo(
                label = "77",
                description = "이번주에 한 커밋",
                type = GrowChartType.Baekjoon,
                chartData = GrowChartData.dummy
            )
        ) {

        }
    }
}