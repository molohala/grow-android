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
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.util.GrowPreviews
import com.molohala.grow.designsystem.legacy.shimmer.shimmerEffect

@Composable
fun GrowAvatarShimmer(
    modifier: Modifier = Modifier,
    type: AvatarType
) {
    Box(
        modifier = modifier
            .size(type.size)
            .background(shimmerEffect(), CircleShape)
    )
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
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