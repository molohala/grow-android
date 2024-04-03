package com.molohala.infinity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GithubRankCell(
    rank: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .bounceClick(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$rank",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .clip(CircleShape)
        )
    }
}