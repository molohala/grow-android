package com.molohala.grow.designsystem.component.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.extension.bounceClick

@Composable
fun InfinitySelector(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val foregroundColor = if (isSelected) Color.White else Color.Gray
    val backgroundColor = if (isSelected) GrowColor.blue else GrowColor.background

    Text(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        text = text,
        color = foregroundColor,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}

/**
 *             Text(text)
 *                 .font(.callout)
 *                 .frame(height: Self.categoryHeight)
 *                 .padding(.horizontal, 16)
 *                 .foregroundStyle(foregroundColor)
 *                 .background(backgroundColor)
 *                 .clipShape(RoundedRectangle(cornerRadius: Self.categoryHeight / 2))
 *         }
 *         .applyAnimation()
 *     }
 * }
 *
 */

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InfinitySelector(text = "이번 주", isSelected = true) {

        }
        InfinitySelector(text = "전체", isSelected = false) {

        }
    }
}