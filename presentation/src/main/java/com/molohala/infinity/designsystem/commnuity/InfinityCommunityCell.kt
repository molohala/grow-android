package com.molohala.infinity.commnuity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.infinity.R
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.extension.bounceClick


@Composable
fun InfinityCommunityCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .size(36.dp)
            )
            Column {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "노영재",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = "1시간 전",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .bounceClick(onClick = {

                    }),
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "more",
                tint = Color.LightGray
            )
        }
        Text(
            text = "지존지존지존지존지존지존지존지존지존지존지존지존지존지존지존",
            style = MaterialTheme.typography.bodyLarge
        )
        Row {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .bounceClick(onClick = {

                    }),
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = null,
                tint = Color.Red
            )
            Text(
                text = "10",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .bounceClick(onClick = {

                    })
                    .padding(start = 8.dp),
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = null,
                tint = Color.LightGray
            )
            Text(
                text = "10",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}