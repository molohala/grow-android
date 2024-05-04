package com.molohala.grow.designsystem.specific.chart

import androidx.annotation.DrawableRes
import com.molohala.grow.R
import com.molohala.grow.common.chart.GrowChartData

sealed class GrowChartType(
    @DrawableRes val icon: Int
) {
    data object Github: GrowChartType(icon = R.drawable.ic_github)
    data object Baekjoon: GrowChartType(icon = R.drawable.ic_baekjoon)
}

data class GrowChartInfo(
    val label: String,
    val description: String,
    val type: GrowChartType,
    val chartData: GrowChartData
)