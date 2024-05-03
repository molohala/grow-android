package com.molohala.grow.designsystem.foundation.shadow

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.shadow
import com.molohala.grow.designsystem.foundation.GrowTheme

sealed class GrowShadow(
    val y: Int,
    val blur: Int
) {
    data object elevationBlack1 : GrowShadow(y = 3, blur = 10)
    data object elevationBlack2 : GrowShadow(y = 4, blur = 12)
    data object elevationBlack3 : GrowShadow(y = 6, blur = 18)
}

@Composable
fun Modifier.growShadow(type: GrowShadow): Modifier {

    val color = when (type) {
        GrowShadow.elevationBlack1 -> GrowTheme.colorScheme.elevationBlack1
        GrowShadow.elevationBlack2 -> GrowTheme.colorScheme.elevationBlack2
        GrowShadow.elevationBlack3 -> GrowTheme.colorScheme.elevationBlack2
    }

    return this
        .shadow(
            offsetY = type.y.dp,
            color = color,
            blur = type.blur.dp
        )
}

@Preview
@Composable
private fun Preview() {
    GrowTheme {
        Row {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .growShadow(type = GrowShadow.elevationBlack1)
                    .clip(RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .growShadow(type = GrowShadow.elevationBlack1)
                    .clip(RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .growShadow(type = GrowShadow.elevationBlack1)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    com.molohala.grow.designsystem.foundation.shadow.Preview()
}