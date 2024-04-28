package com.molohala.infinity.ui.main.profile.setting

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
import com.molohala.infinity.R
import com.molohala.infinity.color.InfinityColor
import com.molohala.infinity.typo.TopBar
import com.molohala.infinity.ui.main.main.NavGroup
import com.molohala.infinity.ui.main.profile.Logout
import com.molohala.infinity.ui.main.profile.setting.component.SettingCell


@Composable
fun SettingScreen(
    navController: NavController
) {
    TopBar(
        text = "설정",
        backgroundColor = InfinityColor.background,
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .background(InfinityColor.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingCell(
                modifier = Modifier
                    .padding(top = 16.dp),
                icon = R.drawable.ic_profile,
                iconColor = Color.Gray,
                text = "프로필 설정"
            ) {
                navController.navigate(NavGroup.ProfileEdit.name)
            }
            SettingCell(
                icon = R.drawable.ic_github,
                iconColor = InfinityColor.github,
                text = "Github 설정",
                description = "bestswlkh0310"
            ) {
                navController.navigate(NavGroup.GithubSetting.name)
            }
            SettingCell(
                icon = R.drawable.ic_baekjoon,
                iconColor = InfinityColor.baekjoon,
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

            }
        }
    }
}