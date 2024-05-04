package com.molohala.grow.common.chart

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point
import com.molohala.grow.common.util.datesForWeek
import com.molohala.grow.common.util.monthPerDay
import com.molohala.grow.data.info.response.CommitResponse
import com.molohala.grow.designsystem.specific.chart.GrowChartInfo
import com.molohala.grow.designsystem.specific.chart.GrowChartType
import kotlin.random.Random.Default.nextInt

data class GrowChartData(
    val points: List<Point>,
    val color: Color
) {
    companion object {
        val dummy = GrowChartData(
            points = datesForWeek.mapIndexed { idx, it ->
                Point(
                    x = idx.toFloat(),
                    y = nextInt(from = 0, until = 30).toFloat(),
                    description = it.monthPerDay
                )
            },
            color = Color.Red
        )
    }
}

val List<CommitResponse>.githubWeekChartInfo: GrowChartInfo
    get() = GrowChartInfo(
        label = this.sumOf { it.contributionCount }.toString(),
        description = "이번주에 한 커밋",
        type = GrowChartType.Github,
        chartData = GrowChartData(
            points = this.mapIndexed { idx, it -> Point(x = idx.toFloat(), y = it.contributionCount.toFloat(), it.date.monthPerDay) },
            color = Color.Red
        )
    )