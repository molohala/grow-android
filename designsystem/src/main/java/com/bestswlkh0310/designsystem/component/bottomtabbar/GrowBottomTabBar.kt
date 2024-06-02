package com.bestswlkh0310.designsystem.component.bottomtabbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bestswlkh0310.designsystem.R
import com.bestswlkh0310.designsystem.extension.bounceClick
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.iconography.GrowIcon
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews

sealed class BottomTabItemType(
    @DrawableRes val resId: Int,
    val text: String
) {
    data object Home : BottomTabItemType(R.drawable.ic_home, "홈")
    data object Forum : BottomTabItemType(R.drawable.ic_chat, "포럼")
    data object Github : BottomTabItemType(R.drawable.ic_github, "Github")
    data object Baekjoon : BottomTabItemType(R.drawable.ic_baekjoon, "백준")
    data object Profile : BottomTabItemType(R.drawable.ic_person, "프로필")
}

private val bottomTabList = arrayOf(
    BottomTabItemType.Home,
    BottomTabItemType.Forum,
    BottomTabItemType.Github,
    BottomTabItemType.Baekjoon,
    BottomTabItemType.Profile,
)

@Composable
fun GrowBottomTabBar(
    modifier: Modifier = Modifier,
    selected: BottomTabItemType,
    onClick: (BottomTabItemType) -> Unit
) {
    val shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = GrowTheme.colorScheme.background,
                shape = shape,
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = GrowTheme.colorScheme.bottomTabSecondary
                ),
                shape = shape
            )
            .padding(8.dp)
            .navigationBarsPadding()
    ) {
        bottomTabList.forEach {
            GrowBottomTabItem(
                modifier = Modifier
                    .weight(1f),
                type = it,
                selected = selected == it
            ) {
                onClick(it)
            }
        }
    }
}

@Composable
private fun GrowBottomTabItem(
    modifier: Modifier = Modifier,
    type: BottomTabItemType,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animColor by animateColorAsState(
        targetValue = if (selected) GrowTheme.colorScheme.bottomTabPrimary else GrowTheme.colorScheme.bottomTabPrimaryDisabled,
        label = "",
    )

    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .bounceClick(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GrowIcon(
            modifier = Modifier
                .size(26.dp),
            id = type.resId,
            color = animColor
        )
        Text(
            text = type.text,
            style = GrowTheme.typography.labelRegular.copy(fontSize = 10.sp),
            color = animColor,
        )
    }
}

@Composable
@GrowPreviews
private fun Preview() {
    var selectedItem: BottomTabItemType by remember { mutableStateOf(BottomTabItemType.Home) }
    GrowTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrowTheme.colorScheme.background),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            GrowBottomTabBar(
                modifier = Modifier,
                selected = selectedItem,
                onClick = {
                    selectedItem = it
                },
            )
        }
    }
}