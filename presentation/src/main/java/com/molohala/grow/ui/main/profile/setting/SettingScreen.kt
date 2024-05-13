package com.molohala.grow.ui.main.profile.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.common.util.getVersionInfo
import com.molohala.grow.designsystem.component.button.GrowToggle
import com.molohala.grow.designsystem.component.dialog.GrowDialog
import com.molohala.grow.designsystem.component.divider.GrowDivider
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.specific.settingcell.GrowSettingCell
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppViewModel


@Composable
fun SettingScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: SettingViewModel = viewModel()
) {

    val context = LocalContext.current
    val uiAppState by appViewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current
    var showRemoveMemberDialog by remember { mutableStateOf(false) }
    var showRemoveMemberFailureDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                SettingSideEffect.RemoveMemberFailure -> {
                    showRemoveMemberFailureDialog = true
                }

                SettingSideEffect.RemoveMemberSuccess -> {
                    appViewModel.clearToken()
                }
            }
        }
    }

    GrowTopAppBar(
        text = "설정",
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val profile = uiAppState.profile as? FetchFlow.Success
                    GrowSettingCell(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        leftIcon = R.drawable.ic_person,
                        label = "프로필 설정",
                        description = profile?.data?.name ?: ""
                    ) {
                        navController.navigate(NavGroup.ProfileEdit.name)
                    }
                    val github =
                        profile?.data?.socialAccounts?.firstOrNull { it.socialType == "GITHUB" }
                    GrowSettingCell(
                        leftIcon = R.drawable.ic_github,
                        label = "Github 설정",
                        description = github?.socialId ?: ""
                    ) {
                        navController.navigate(NavGroup.GithubSetting.name)
                    }
                    val baekjoon =
                        profile?.data?.socialAccounts?.firstOrNull { it.socialType == "SOLVED_AC" }
                    GrowSettingCell(
                        leftIcon = R.drawable.ic_baekjoon,
                        label = "백준 설정",
                        description = baekjoon?.socialId ?: ""
                    ) {
                        navController.navigate(NavGroup.BaekjoonSetting.name)
                    }
                }
            }
            item {
                GrowDivider()
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
//                    GrowSettingCell(
//                        label = "알림 허용",
//                        leftIcon = R.drawable.ic_notification,
//                        content = {
//                            GrowToggle(checked = true) {
//
//                            }
//                        }
//                    )
                    GrowSettingCell(
                        label = "다크모드",
                        leftIcon = R.drawable.ic_moon,
                        content = {
                            GrowToggle(
                                checked = uiAppState.isDarkMode,
                                onCheckedChange = appViewModel::updateIsDarkMode
                            )
                        }
                    )
                }
            }
            item {
                GrowDivider()
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GrowSettingCell(
                        label = "로그아웃"
                    ) {
                        appViewModel.clearToken()
                    }
                    GrowSettingCell(
                        label = "회원탈퇴",
                        labelColor = GrowTheme.colorScheme.textWarning
                    ) {
                        showRemoveMemberDialog = true
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "버전 ${getVersionInfo(context) ?: "-"}",
                        style = GrowTheme.typography.labelMedium,
                        color = GrowTheme.colorScheme.textAlt
                    )
                    Text(
                        modifier = Modifier
                            .bounceClick(onClick = {
                                uriHandler.openUri("https://ssseqew.notion.site/f7614db9bb7e489ab209f891e28633cc?pvs=4")
                            }),
                        text = "개인정보 이용 약관",
                        style = GrowTheme.typography.labelMedium,
                        color = GrowTheme.colorScheme.textAlt,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        modifier = Modifier
                            .bounceClick(onClick = {
                                uriHandler.openUri("https://ssseqew.notion.site/10ad68a929c44d45bae4ea40535876a2?pvs=4")
                            }),
                        text = "서비스 정책",
                        style = GrowTheme.typography.labelMedium,
                        color = GrowTheme.colorScheme.textAlt,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }

    if (showRemoveMemberDialog) {
        GrowDialog(
            title = "",
            onCancelRequest = {
                showRemoveMemberDialog = false
            },
            onSuccessRequest = {
                viewModel.removeMember()
            },
            onDismissRequest = {
                showRemoveMemberDialog = false
            }
        )
    }

    if (showRemoveMemberFailureDialog) {
        GrowDialog(
            title = "회원 탈퇴에 실패 했습니다"
        ) {
            showRemoveMemberFailureDialog = false
        }
    }
}