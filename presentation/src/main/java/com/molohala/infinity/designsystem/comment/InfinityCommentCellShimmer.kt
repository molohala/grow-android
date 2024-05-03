package com.molohala.infinity.designsystem.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.infinity.common.util.timeAgo
import com.molohala.infinity.data.comment.response.CommentResponse
import com.molohala.infinity.designsystem.shimmer.shimmerEffect

@Composable
fun InfinityCommentCellShimmer() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(shimmerEffect(), RoundedCornerShape(4.dp))
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(30.dp)
                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(100.dp)
                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
@Preview
fun Preview() {
    InfinityCommentCellShimmer()
}