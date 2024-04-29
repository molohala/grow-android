package com.molohala.infinity.designsystem.github

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.infinity.designsystem.shimmer.shimmerEffect
import com.molohala.infinity.extension.applyCardView

@Composable
fun InfinityGithubRankCellShimmer(
    modifier: Modifier = Modifier,
    isCard: Boolean = false,
) {

    val width by remember {
        mutableIntStateOf((2..<4).random())
    }

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 60.dp)
            .then(if (isCard) Modifier.applyCardView() else Modifier),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(shimmerEffect(), RoundedCornerShape(4.dp)),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(width / 10.0f)
                .height(20.dp)
                .background(shimmerEffect(), RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(20.dp)
                .background(shimmerEffect(), RoundedCornerShape(4.dp))
        )
    }
}

@Preview
@Composable
private fun GithubRankPreview() {
    InfinityGithubRankCellShimmer()
}