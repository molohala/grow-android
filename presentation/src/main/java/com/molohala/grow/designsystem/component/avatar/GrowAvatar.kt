package com.molohala.grow.designsystem.component.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.molohala.grow.R
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

sealed class AvatarType(
    val size: Dp
) {
    data object ExtraSmall: AvatarType(size = 16.dp)
    data object Small: AvatarType(size = 24.dp)
    data object Medium: AvatarType(size = 32.dp)
    data object Large: AvatarType(size = 36.dp)
    data object ExtraLarge: AvatarType(size = 64.dp)
    data object XXL: AvatarType(size = 128.dp)
}

@Composable
fun GrowAvatar(
    modifier: Modifier = Modifier,
    image: String? = null,
    type: AvatarType
) {
    if (image == null) {
        Box(
            modifier = modifier
                .size(type.size)
                .background(
                    color = GrowTheme.colorScheme.avatarBackground,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            GrowIcon(
                modifier = Modifier.size(type.size / 2),
                id = R.drawable.ic_person,
                color = GrowTheme.colorScheme.avatarLabel
            )
        }
    } else {
        AsyncImage(
            modifier = modifier
                .size(type.size)
                .clip(CircleShape),
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            GrowAvatar(type = AvatarType.Large)
        }
    }
}