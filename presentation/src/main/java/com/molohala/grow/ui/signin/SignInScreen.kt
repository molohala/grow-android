package com.molohala.grow.ui.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.button.InfinityButton
import com.molohala.grow.designsystem.component.TopBar
import com.molohala.grow.designsystem.component.textfield.InfinityTextField
import com.molohala.grow.ui.root.AppViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = viewModel(),
    appViewModel: AppViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                is SignInSideEffect.LoginSuccess -> {
                    appViewModel.updateAccessToken(it.accessToken)
                    appViewModel.updateRefreshToken(it.refreshToken)
                }
            }
        }
    }
    TopBar(
        text = "로그인"
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfinityTextField(
                placeholder = "아이디를 입력해 주세요",
                value = uiState.id,
                onValueChange = viewModel::updateId
            )
            InfinityTextField(
                placeholder = "비밀번호를 입력해 주세요",
                value = uiState.pw,
                isPassword = true,
                onValueChange = viewModel::updatePw
            )
            Spacer(modifier = Modifier.weight(1f))
            InfinityButton(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                text = "도담도담 로그인"
            ) {
                viewModel.signIn()
            }
        }
    }
}