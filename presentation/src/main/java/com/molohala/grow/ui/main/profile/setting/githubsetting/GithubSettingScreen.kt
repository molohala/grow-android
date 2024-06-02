package com.molohala.grow.ui.main.profile.setting.githubsetting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.molohala.grow.common.flow.FetchFlow
import com.bestswlkh0310.mydesignsystem.component.button.MyCTAButton
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.R
import com.molohala.grow.specific.text.Headline
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

    MyTopAppBar(
        text = "Github 설정",
        onClickBackButton = {
            navController.popBackStack()
        },
        backgroundColor = MyTheme.colorScheme.backgroundAlt
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Headline(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 4.dp),
                    text = "Github ID"
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            MyTextField(
                value = uiState.githubId,
                onValueChange = viewModel::updateGithubId,
                hint = "",
            )
            Spacer(modifier = Modifier.weight(1f))
            MyCTAButton(
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
        MyDialog(
            title = "Github 정보 수정 성공",
            onDismissRequest = {
                showPatchGithubSettingSuccessDialog = false
                navController.popBackStack()
            },
        )
    }

    if (showPatchGithubSettingFailureDialog) {
        MyDialog(
            title = "Github 정보 수정 실패",
            content = "아이디를 다시 확인해 주세요",
            onDismissRequest = {
                showPatchGithubSettingFailureDialog = false
            },
        )
    }
}
