package com.molohala.grow.designsystem.component.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.bestswlkh0310.designsystem.foundation.GrowTheme
import com.bestswlkh0310.designsystem.foundation.util.GrowPreviews

sealed class MenuType {
    data object Normal : MenuType()
    data object Destructive : MenuType()
}

data class GrowMenuData(
    val label: String,
    val type: MenuType = MenuType.Normal,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val onClick: () -> Unit,
)

@Composable
fun GrowMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    menuList: List<GrowMenuData>,
    onDismissRequest: () -> Unit,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
) {

    DropdownMenu(
        modifier = modifier.background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.backgroundAlt),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = offset,
        properties = properties
    ) {
        for (menu in menuList) {

        }
        menuList.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it.label,
                        color = if (it.type is MenuType.Normal) com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textNormal else com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.textWarning,
                        style = com.bestswlkh0310.designsystem.foundation.GrowTheme.typography.bodyBold
                    )
                }, onClick = {
                    it.onClick()
                    onDismissRequest()
                }
            )
        }
    }
}

@com.bestswlkh0310.designsystem.foundation.util.GrowPreviews
@Composable
private fun Preview() {
    com.bestswlkh0310.designsystem.foundation.GrowTheme {
        Box(
            modifier = Modifier
                .background(com.bestswlkh0310.designsystem.foundation.GrowTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            GrowMenu(
                expanded = true,
                menuList = listOf(
                    GrowMenuData(
                        label = "수정하기"
                    ) {},
                    GrowMenuData(
                        type = MenuType.Destructive,
                        label = "삭제하기"
                    ) {}
                ),
                onDismissRequest = { /*TODO*/ }
            )
        }
    }
}