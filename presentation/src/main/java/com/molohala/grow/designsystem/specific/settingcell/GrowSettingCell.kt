package com.molohala.grow.designsystem.specific.settingcell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.molohala.grow.R
import com.molohala.grow.designsystem.component.button.GrowToggle
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.util.GrowPreviews

@Composable
fun GrowSettingCell(
    modifier: Modifier = Modifier,
    label: String,
    labelColor: Color = GrowTheme.colorScheme.textNormal,
    leftIcon: Int? = null,
    leftIconColor: Color = GrowTheme.colorScheme.textAlt,
    description: String? = null,
    content: @Composable () -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .then(if (onClick == null) Modifier else Modifier.bounceClick(onClick = onClick))
            .applyCardView()
            .padding(horizontal = 12.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            leftIcon?.let {
                GrowIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = it,
                    color = leftIconColor
                )
            }
            Text(
                text = label,
                style = GrowTheme.typography.bodyBold,
                color = labelColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        description?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = it,
                    style = GrowTheme.typography.labelMedium,
                    color = GrowTheme.colorScheme.textAlt
                )
                GrowIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = R.drawable.ic_expand_right,
                    color = GrowTheme.colorScheme.textAlt
                )
            }
        }
        content()
    }
}

@GrowPreviews
@Composable
private fun Preview() {
    GrowTheme {
        Column(
            modifier = Modifier
                .background(GrowTheme.colorScheme.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowSettingCell(
                label = "백준 설정",
                leftIcon = R.drawable.ic_baekjoon,
                description = "bestswlkh0310"
            ) {}
            GrowSettingCell(
                label = "알림 켜기",
                leftIcon = R.drawable.ic_notification,
                content = {
                    GrowToggle(checked = true) {}
                }
            ) {}
        }
    }
}