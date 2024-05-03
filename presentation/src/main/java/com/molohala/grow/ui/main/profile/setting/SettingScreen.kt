package com.molohala.grow.ui.main.profile.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.legacy.TopBar
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.main.profile.Logout
import com.molohala.grow.ui.main.profile.setting.component.SettingCell
import com.molohala.grow.ui.root.AppViewModel


@Composable
fun SettingScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    TopBar(
        text = "설정",
        backgroundColor = GrowColor.background,
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .background(GrowColor.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingCell(
                modifier = Modifier
                    .padding(top = 16.dp),
                icon = R.drawable.ic_person,
                iconColor = Color.Gray,
                text = "프로필 설정"
            ) {
                navController.navigate(NavGroup.ProfileEdit.name)
            }
            SettingCell(
                icon = R.drawable.ic_github,
                iconColor = GrowColor.github,
                text = "Github 설정",
                description = "bestswlkh0310"
            ) {
                navController.navigate(NavGroup.GithubSetting.name)
            }
            SettingCell(
                icon = R.drawable.ic_baekjoon,
                iconColor = GrowColor.baekjoon,
                text = "백준 설정",
                description = "hhhello0507"
            ) {
                navController.navigate(NavGroup.BaekjoonSetting.name)
            }
            Spacer(modifier = Modifier.weight(1f))
            Logout(
                modifier = Modifier
                    .padding(bottom = 48.dp)
            ) {
                appViewModel.clearToken()
            }
        }
    }
}