package com.molohala.grow.ui.main.profile.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.specific.settingcell.GrowSettingCell
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.main.profile.Logout
import com.molohala.grow.ui.root.AppViewModel


@Composable
fun SettingScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    GrowTopAppBar(
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
            GrowSettingCell(
                modifier = Modifier
                    .padding(top = 16.dp),
                leftIcon = R.drawable.ic_person,
                label = "프로필 설정"
            ) {
                navController.navigate(NavGroup.ProfileEdit.name)
            }
            GrowSettingCell(
                leftIcon = R.drawable.ic_github,
                label = "Github 설정",
                description = "bestswlkh0310"
            ) {
                navController.navigate(NavGroup.GithubSetting.name)
            }
            GrowSettingCell(
                leftIcon = R.drawable.ic_baekjoon,
                label = "백준 설정",
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