//package com.molohala.infinity.chart
//
//import android.graphics.PointF
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithCache
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.Fill
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.drawscope.clipRect
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.molohala.infinity.color.InfinityColor
//import java.time.LocalDate
//
//@Composable
//fun InfinityChart(
//    modifier: Modifier = Modifier,
//    color: Color
//) {
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//    ) {
//        val animationProgress = remember { Animatable(0f) }
//
//        LaunchedEffect(key1 = graphData, block = {
//            animationProgress.animateTo(1f, tween(3000))
//        })
//
//        Spacer(
//            modifier = Modifier
//                .padding(3.dp)
//                .fillMaxSize()
//                .align(Alignment.Center)
//                .drawWithCache {
//                    val path = generateSmoothPath(graphData, size)
//                    val filledPath = Path()
//                    filledPath.addPath(path)
//                    filledPath.relativeLineTo(0f, size.height)
//                    filledPath.lineTo(0f, size.height)
//                    filledPath.close()
//
//                    onDrawBehind {
//                        val barWidthPx = 1.dp.toPx()
//                        val horizontalLines = 3
//                        val sectionSize = size.height / (horizontalLines + 1)
//                        repeat(horizontalLines) { i ->
//                            val startY = sectionSize * (i + 1)
//                            drawLine(
//                                BarColor,
//                                start = Offset(0f, startY),
//                                end = Offset(size.width, startY),
//                                strokeWidth = barWidthPx
//                            )
//                        }
//
//                        // draw line
//                        clipRect(right = size.width * animationProgress.value) {
//                            drawPath(path, color, style = Stroke(3.dp.toPx()))
//
//                            drawPath(
//                                filledPath,
//                                brush = Brush.verticalGradient(
//                                    listOf(
//                                        color.copy(alpha = 0.4f),
//                                        Color.Transparent
//                                    )
//                                ),
//                                style = Fill
//                            )
//                        }
//                    }
//                })
//    }
//}
//
//fun generateSmoothPath(data: List<Balance>, size: Size): Path {
//    val path = Path()
//    val numberEntries = data.size - 1
//    val weekWidth = size.width / numberEntries
//
//    val max = data.maxBy { it.amount }
//    val heightPxPerAmount = size.height / max.amount.toFloat()
//
//    var previousBalanceX = 0f
//    var previousBalanceY = 0f
//    data.forEachIndexed { i, balance ->
//        val balanceX = i * weekWidth
//        val balanceY = size.height - balance.amount.toFloat() * heightPxPerAmount
//
//        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
//        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
//        path.cubicTo(
//            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
//            balanceX, balanceY
//        )
//
//        previousBalanceX = balanceX
//        previousBalanceY = balanceY
//    }
//    return path
//}
//
//val graphData = listOf(
//    Balance(LocalDate.now().plusWeeks(0), 10),
//    Balance(LocalDate.now().plusWeeks(1), 23),
//    Balance(LocalDate.now().plusWeeks(2), 10),
//    Balance(LocalDate.now().plusWeeks(3), 5),
//    Balance(LocalDate.now().plusWeeks(4), 20),
//    Balance(LocalDate.now().plusWeeks(5), 23),
//)
//
//data class Balance(val date: LocalDate, val amount: Int)
//
//val BarColor = InfinityColor.gray300
//
//@Preview
//@Composable
//fun ChartPreview() {
//    Box(
//        modifier = Modifier
//            .background(Color.White)
//            .height(200.dp)
//    ) {
//        InfinityChart(
//            color = Color(0xFFFF8125)
//        )
//    }
//}