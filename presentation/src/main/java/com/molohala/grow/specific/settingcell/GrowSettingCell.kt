package com.molohala.grow.specific.settingcell

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
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.button.MyToggle
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews

@Composable
fun GrowSettingCell(
    modifier: Modifier = Modifier,
    label: String,
    labelColor: Color = MyTheme.colorScheme.textNormal,
    leftIcon: Int? = null,
    leftIconColor: Color = MyTheme.colorScheme.textAlt,
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
                MyIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = it,
                    color = leftIconColor
                )
            }
            Text(
                text = label,
                style = MyTheme.typography.bodyBold,
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
                    style = MyTheme.typography.labelMedium,
                    color = MyTheme.colorScheme.textAlt
                )
                MyIcon(
                    modifier = Modifier
                        .size(24.dp),
                    id = R.drawable.ic_expand_right,
                    color = MyTheme.colorScheme.textAlt
                )
            }
        }
        content()
    }
}

@MyPreviews
@Composable
private fun Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MyTheme.colorScheme.background)
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
                    MyToggle(checked = true) {}
                }
            ) {}
        }
    }
}