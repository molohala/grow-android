package com.molohala.grow.specific.chart

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
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews
import com.molohala.My.specific.chart.MyChart
import com.molohala.grow.specific.motivation.GrowMotivationCell

@Composable
fun GrowChartCell(
    modifier: Modifier = Modifier,
    chartInfo: GrowChartInfo,
    onClick: () -> Unit
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
                Text(
                    text = chartInfo.label,
                    style = MyTheme.typography.title1B,
                    color = MyTheme.colorScheme.textNormal
                )
                Text(
                    text = chartInfo.description,
                    style = MyTheme.typography.labelMedium,
                    color = MyTheme.colorScheme.textDarken
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            val color = when (chartInfo.type) {
                GrowChartType.Baekjoon -> MyTheme.colorScheme.baekjoon
                GrowChartType.Github -> MyTheme.colorScheme.github
            }
            MyIcon(
                id = chartInfo.type.icon,
                color = color
            )
        }
        if (chartInfo.chartData.getSum() > 0) {
            MyChart(
                modifier = Modifier
                    .height(200.dp),
                chartData = chartInfo.chartData,
                background = MyTheme.colorScheme.background
            )
        } else {
            GrowMotivationCell()
        }
    }
}

@MyPreviews
@Composable
private fun Preview() {
    MyTheme {
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