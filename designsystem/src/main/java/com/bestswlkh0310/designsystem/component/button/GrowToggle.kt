package com.molohala.grow.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.shadow.ShadowType
import com.bestswlkh0310.designsystem.foundation.shadow.growShadow
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews


@Composable
fun GrowToggle(
    modifier: Modifier = Modifier,
    checked: Boolean,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background,
        checkedTrackColor = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.buttonPrimary,
        uncheckedThumbColor = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background,
        uncheckedTrackColor = com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.buttonTextDisabled,
        uncheckedBorderColor = Color.Transparent
    ),
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors,
        modifier = modifier
            .height(31.dp)
            .width(51.dp),
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(27.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .growShadow(com.bestswlkh0310.designsystem.foundation.shadow.ShadowType.ElevationBlack1),
            )
        },
    )
}

@Composable
@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Column(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowToggle(checked = true) {}
            GrowToggle(checked = false) {}
        }
    }
}