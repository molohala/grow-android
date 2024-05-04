package com.molohala.grow.designsystem.specific.commnuity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.legacy.shimmer.shimmerEffect


@Composable
fun GrowCommunityCellShimmer(
    modifier: Modifier = Modifier
) {

    val contentWidth1 by remember {
        mutableFloatStateOf((2..<4).random() / 10f)
    }
    val contentWidth2 by remember {
        mutableFloatStateOf((6..<8).random() / 10f)
    }

    Column(
        modifier = modifier
            .applyCardView(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(shimmerEffect())
                    .size(36.dp)
            )
            Column {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(20.dp)
                        .width(50.dp)
                        .background(
                            shimmerEffect(),
                            RoundedCornerShape(4.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(contentWidth1)
                    .height(20.dp)
                    .background(
                        shimmerEffect(), RoundedCornerShape(
                            4.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(contentWidth2)
                    .height(20.dp)
                    .background(
                        shimmerEffect(),
                        RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

@Preview
@Composable
private fun ShimmerPriview() {
    GrowCommunityCellShimmer()
}