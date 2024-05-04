package com.molohala.grow.designsystem.specific.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowRankCell(
    modifier: Modifier = Modifier,
    name: String,
    socialId: String,
    rank: Int,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rank.toString(),
                style = GrowTheme.typography.bodyRegular,
                color = GrowTheme.colorScheme.textNormal
            )
            GrowAvatar(type = AvatarType.Large)
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = name,
                        style = GrowTheme.typography.bodyMedium,
                        color = GrowTheme.colorScheme.textNormal
                    )
                    val medal = when (rank) {
                        1 -> R.drawable.first_medal
                        2 -> R.drawable.second_medal
                        3 -> R.drawable.third_medal
                        else -> null
                    } ?: return@Row
                    Image(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = medal),
                        contentDescription = null
                    )
                }
                Text(
                    text = socialId,
                    style = GrowTheme.typography.bodyMedium,
                    color = GrowTheme.colorScheme.textAlt
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = label,
            style = GrowTheme.typography.bodyBold,
            color = GrowTheme.colorScheme.textNormal
        )
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            GrowRankCell(name = "박주영", socialId = "jombidev", rank = 1, label = "10 문제") {

            }
            GrowRankCell(name = "박주영", socialId = "jombidev", rank = 2, label = "10 문제") {

            }
            GrowRankCell(name = "박주영", socialId = "jombidev", rank = 3, label = "10 문제") {

            }
            GrowRankCell(name = "박주영", socialId = "jombidev", rank = 4, label = "10 문제") {

            }
        }
    }
}