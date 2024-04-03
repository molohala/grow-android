package com.molohala.infinity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CommunityCell() {
    Column(
        modifier = Modifier
            .bounceClick(onClick = {

            })
            .applyCardView(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .size(48.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = "노영재",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = "1시간 전",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Text(text = "지존지존지존지존지존지존지존지존지존지존지존지존지존지존지존")
    }
}