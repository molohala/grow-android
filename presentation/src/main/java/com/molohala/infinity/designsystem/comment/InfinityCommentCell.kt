package com.molohala.infinity.designsystem.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.molohala.infinity.common.util.timeAgo
import com.molohala.infinity.data.comment.response.CommentResponse

@Composable
fun InfinityCommentCell(
    comment: CommentResponse,
    onClickRemove: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Column {
            Profile(comment = comment)
            Text(comment.content, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun Profile(comment: CommentResponse) {
    Row {
        Text(
            text = comment.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = comment.createdAt.timeAgo,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { /*TODO*/ }) {

        }
    }
}