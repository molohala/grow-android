package com.molohala.grow.ui.main.profile.setting.baekjoonsetting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.designsystem.component.button.GrowCTAButton
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme

@Composable
fun BaekjoonSettingScreen(
    navController: NavController,
    viewModel: BaekjoonSettingViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

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
            ) {
                navController.popBackStack()
            }
        }
    }
}