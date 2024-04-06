package com.molohala.infinity.ui.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.molohala.infinity.InfinityButton
import com.molohala.infinity.InfinityTextField
import com.molohala.infinity.Title

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(text = "로그인")
        InfinityTextField(
            placeholder = "아이디를 입력해 주세요",
            value = uiState.id,
            onValueChange = viewModel::updateId
        )
        InfinityTextField(
            placeholder = "비밀번호를 입력해 주세요",
            value = uiState.pw,
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