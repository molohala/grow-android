package com.molohala.grow.ui.main.profile.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick

@Composable
fun SettingCell(
    modifier: Modifier = Modifier,
    icon: Int,
    iconColor: Color,
    text: String,
    description: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconColor
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
//        IconRightArrow()
    }
}
//            HStack {
//            }
//                .padding(.horizontal, 8)
//            .padding(.vertical, 12)
//            .applyCardView()
//        }
//                .applyAnimation()
//    }
//}
