package com.molohala.grow.specific.chart

import androidx.annotation.DrawableRes
import com.bestswlkh0310.mydesignsystem.R

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