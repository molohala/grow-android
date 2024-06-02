package com.molohala.grow.specific.chart

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point
import com.bestswlkh0310.mydesignsystem.foundation.util.datesForWeek
import com.bestswlkh0310.mydesignsystem.foundation.util.monthPerDay
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
            color = Color.Green
        )
    }

    fun getSum(): Int {
        return points.sumOf { it.y.toInt() }
    }
}