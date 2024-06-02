package com.molohala.grow.designsystem.component.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.component.avatar.AvatarType

@Composable
fun GrowAvatarShimmer(
    modifier: Modifier = Modifier,
    type: AvatarType
) {
    Box(
        modifier = modifier
            .size(type.size)
            .background(com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect(), CircleShape)
    )
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowAvatarShimmer(type = AvatarType.Large)
            GrowAvatarShimmer(type = AvatarType.Medium)
            GrowAvatarShimmer(type = AvatarType.Small)
        }
    }
}