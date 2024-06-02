package com.molohala.grow.ui.main.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect
import com.molohala.grow.designsystem.specific.comment.GrowCommentCellShimmer

@Composable
fun ForumDetailCellShimmer() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Profile()
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(20.dp)
                    .background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect(), RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(20.dp)
                    .background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect(), RoundedCornerShape(4.dp))
            )
        }
        Divider(modifier = Modifier.background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect()))
        Comments()
    }
}

@Composable
private fun Profile() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect(), CircleShape)
        )
        Box(modifier = Modifier
            .width(40.dp)
            .height(20.dp)
            .background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect(), RoundedCornerShape(4.dp)))
    }
}

@Composable
private fun Comments() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(3) {
            GrowCommentCellShimmer()
        }
    }
}

@Composable
@Preview
private fun Preview() {
    ForumDetailCellShimmer()
}