package com.bestswlkh0310.designsystem.component.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.shimmer.shimmerEffect
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
import com.molohala.grow.designsystem.component.language.GrowLanguage

@Composable
fun GrowLanguageShimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .width(60.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(shimmerEffect(), RoundedCornerShape(17.dp)),
    )
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
            GrowLanguage(text = "Swift")
            GrowLanguage(text = "Mojo")
            GrowLanguageShimmer()
        }
    }
}