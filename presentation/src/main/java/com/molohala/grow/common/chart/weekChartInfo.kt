package com.molohala.grow.common.chart

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point
import com.bestswlkh0310.designsystem.foundation.util.monthPerDay
import com.molohala.grow.specific.chart.GrowChartData
import com.molohala.grow.data.info.response.CommitResponse
import com.molohala.grow.data.info.response.SolveResponse
import com.molohala.grow.specific.chart.GrowChartInfo
import com.molohala.grow.specific.chart.GrowChartType

val List<CommitResponse>.githubWeekChartInfo: GrowChartInfo
    get() = GrowChartInfo(
        label = this.sumOf { it.contributionCount }.toString(),
        description = "이번주에 한 커밋",
        type = GrowChartType.Github,
        chartData = GrowChartData(
            points = this.mapIndexed { idx, it ->
                Point(
                    x = idx.toFloat(),
                    y = it.contributionCount.toFloat(),
                    it.date.monthPerDay
                )
            },
            color = Color(red = 0, green = 122, blue = 255)
        )
    )

val List<SolveResponse>.baekjoonWeekChartInfo: GrowChartInfo
    get() = GrowChartInfo(
        label = this.sumOf { it.solvedCount }.toString(),
        description = "이번주에 푼 문제",
        type = GrowChartType.Baekjoon,
        chartData = GrowChartData(
            points = this.mapIndexed { idx, it ->
                Point(
                    x = idx.toFloat(),
                    y = it.solvedCount.toFloat(),
                    it.date.monthPerDay
                )
            },
            color = Color(red = 52, green = 199, blue = 89)
        )
    )