package com.molohala.grow.ui.main.profile.setting.baekjoonsetting

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
fun BaekjoonSettingScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: BaekjoonSettingViewModel = viewModel()
) {

    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showPatchBaekjoonSettingSuccessDialog by remember { mutableStateOf(false) }
    var showPatchBaekjoonSettingFailureDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val profile = uiAppState.profile as? FetchFlow.Success
        val baekjoon = profile?.data?.socialAccounts?.firstOrNull { it.socialType == "SOLVED_AC" }
        viewModel.updateBaekjoonId(id = baekjoon?.socialId?: "")
        viewModel.uiEffect.collect {
            when (it) {
                BaekjoonSettingSideEffect.PatchBaekjoonSettingFailure -> {
                    showPatchBaekjoonSettingFailureDialog = true
                }
                BaekjoonSettingSideEffect.PatchBaekjoonSettingSuccess -> {
                    showPatchBaekjoonSettingSuccessDialog = true
                }
            }
        }
    }

    GrowTopAppBar(
        text = "백준 설정",
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
                value = uiState.baekjoonId,
                onValueChange = viewModel::updateBaekjoonId,
                hint = "백준 Id를 입력해 주세요",
            )
            Spacer(modifier = Modifier.weight(1f))
            GrowCTAButton(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                leftIcon = R.drawable.ic_check,
                text = "완료",
                enabled = uiState.baekjoonId.isNotEmpty()
            ) {
                viewModel.patchGithubId()
            }
        }
    }
    if (showPatchBaekjoonSettingSuccessDialog) {
        GrowDialog(
            title = "백준 정보 수정 성공",
            onDismissRequest = {
                showPatchBaekjoonSettingSuccessDialog = false
                navController.popBackStack()
            },
        )
    }

    if (showPatchBaekjoonSettingFailureDialog) {
        GrowDialog(
            title = "백준 정보 수정 실패",
            content = "아이디를 다시 확인해 주세요",
            onDismissRequest = {
                showPatchBaekjoonSettingFailureDialog = false
            },
        )
    }
}