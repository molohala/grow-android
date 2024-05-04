package com.molohala.grow.ui.main.profile.setting.githubsetting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.designsystem.component.button.GrowCTAButton
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar

@Composable
fun GithubSettingScreen(
    navController: NavController,
    viewModel: GithubSettingViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    GrowTopAppBar(
        text = "Github 설정",
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GrowTextField(
                value = uiState.githubId,
                onValueChange = viewModel::updateGithubId,
                hint = "Github Id를 입력해 주세요",
            )

            Spacer(modifier = Modifier.weight(1f))
            GrowCTAButton(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                text = "완료하기",
            ) {
                navController.popBackStack()
            }
        }
    }
}