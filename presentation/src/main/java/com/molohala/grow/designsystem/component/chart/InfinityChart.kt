package com.molohala.grow.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.foundation.util.pxToDp

@Composable
fun InfinityChart(
    modifier: Modifier = Modifier,
    color: Color = GrowColor.orange500,
    points: List<Point>
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val steps = 3
    val xAxisData = AxisData.Builder()
        .steps(pointsData.size - 1)
        .labelData { i -> "${points[i].x}" }
        .axisStepSize(size.width.pxToDp() / pointsData.size)
        .labelAndAxisLinePadding(8.dp)
        .backgroundColor(Color.White)
        .axisConfig(
            config = AxisConfig(
                isAxisLineRequired = false,
            )
        )
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(8.dp)
        .axisConfig(
            config = AxisConfig(
                isAxisLineRequired = false
            )
        )
        .build()
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = color
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = color
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color.copy(alpha = 0.4f),
                                color.copy(alpha = 0f)
                            )
                        ),
                        alpha = 1f
                    )
                )
            ),
        ),
        isZoomAllowed = false,
        paddingRight = 0.dp,
        paddingTop = 16.dp,
        bottomPadding = 0.dp,
        containerPaddingEnd = 0.dp,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            enableVerticalLines = false
        ),
        backgroundColor = Color.White
    )
    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .onSizeChanged {
                size = it
            },
        lineChartData = lineChartData
    )
}

val pointsData: List<Point> = listOf(
    Point(1f, 90f),
    Point(2f, 30f),
    Point(3f, 60f),
    Point(4f, 10f),
    Point(5f, 13f),
    Point(6f, 11f),
    Point(7f, 40f),
)

@Composable
@Preview
fun ChartPreview() {
    InfinityChart(
        modifier = Modifier
            .height(200.dp),
        points = pointsData
    )
}