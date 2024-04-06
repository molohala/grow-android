package com.molohala.infinity.github

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.R
import com.molohala.infinity.color.InfinityColor
import com.molohala.infinity.extension.bounceClick
import com.molohala.infinity.icon.IconRightArrow

@Composable
fun InfinityGithubCell(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = "github rank",
                tint = InfinityColor.github
            )
            Text(
                text = "nohjason",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            IconRightArrow()
        }
        Text(
            modifier = Modifier
                .padding(vertical = 40.dp),
            text = "대충 여기 그래프"
        )
    }
}