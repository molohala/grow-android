package com.molohala.grow.ui.signin

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bestswlkh0310.mydesignsystem.component.button.MyCTAButton
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.molohala.grow.ui.root.AppViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = viewModel(),
    appViewModel: AppViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    var showLoginFailureDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                is SignInSideEffect.LoginSuccess -> {
                    appViewModel.updateAccessToken(it.accessToken)
                    appViewModel.updateRefreshToken(it.refreshToken)
                }

                SignInSideEffect.LoginFailure -> {
                    showLoginFailureDialog = true
                }
            }
        }
    }
    MyTopAppBar(
        text = "로그인"
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MyTextField(
                value = uiState.id,
                onValueChange = viewModel::updateId,
                hint = "아이디를 입력해 주세요"
            )
            MyTextField(
                value = uiState.pw,
                onValueChange = viewModel::updatePw,
                hint = "비밀번호를 입력해 주세요",
                secured = true
            )
            Spacer(modifier = Modifier.weight(1f))
            MyCTAButton(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = "도담도담 로그인",
                enabled = uiState.id.isNotEmpty() && uiState.pw.isNotEmpty(),
                isLoading = uiState.isFetching
            ) {
                viewModel.signIn()
            }
        }
    }

    if (showLoginFailureDialog) {
        MyDialog(
            title = "로그인에 실패했습니다",
            onDismissRequest = {
                showLoginFailureDialog = false
            }
        )
    }
}