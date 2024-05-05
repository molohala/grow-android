package com.molohala.grow.ui.main.profile.setting.githubsetting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.button.GrowCTAButton
import com.molohala.grow.designsystem.component.dialog.GrowDialog
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.ui.root.AppViewModel

@Composable
fun GithubSettingScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: GithubSettingViewModel = viewModel()
) {

    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showPatchGithubSettingSuccessDialog by remember { mutableStateOf(false) }
    var showPatchGithubSettingFailureDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val profile = uiAppState.profile as? FetchFlow.Success
        val github = profile?.data?.socialAccounts?.firstOrNull { it.socialType == "GITHUB" }
        viewModel.updateGithubId(id = github?.socialId ?: "")
        viewModel.uiEffect.collect {
            when (it) {
                GithubSettingSideEffect.PatchGithubSettingFailure -> {
                    showPatchGithubSettingFailureDialog = true
                }

                GithubSettingSideEffect.PatchGithubSettingSuccess -> {
                    showPatchGithubSettingSuccessDialog = true
                }
            }
        }
    }

    GrowTopAppBar(
        text = "Github 설정",
        onClickBackButton = {
            navController.popBackStack()
        },
        backgroundColor = GrowTheme.colorScheme.backgroundAlt
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GrowTextField(
                modifier = Modifier
                    .padding(top = 20.dp),
                value = uiState.githubId,
                onValueChange = viewModel::updateGithubId,
                hint = "Github ID",
            )
            Spacer(modifier = Modifier.weight(1f))
            GrowCTAButton(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = "완료",
                leftIcon = R.drawable.ic_check,
                enabled = uiState.githubId.isNotEmpty()
            ) {
                viewModel.patchGithubId()
            }
        }
    }

    if (showPatchGithubSettingSuccessDialog) {
        GrowDialog(
            title = "Github 정보 수정 성공",
            onDismissRequest = {
                showPatchGithubSettingSuccessDialog = false
                navController.popBackStack()
            },
        )
    }

    if (showPatchGithubSettingFailureDialog) {
        GrowDialog(
            title = "Github 정보 수정 실패",
            content = "아이디를 다시 확인해 주세요",
            onDismissRequest = {
                showPatchGithubSettingFailureDialog = false
            },
        )
    }
}
