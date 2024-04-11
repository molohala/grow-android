package com.molohala.infinity.ui.main.statcell

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.extension.bounceClick

@Composable
fun InfinityStatCell(
    modifier: Modifier = Modifier,
    title: String,
    type: InfinityStatType,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .applyCardView()
            .bounceClick(onClick = onClick),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val number = when (type) {
                is InfinityStatType.Baekjoon -> type.solved
                is InfinityStatType.Github -> type.commit
            }
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = type.icon),
                contentDescription = null,
                tint = type.iconColor
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
