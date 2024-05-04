package com.molohala.grow.ui.main.profile.setting.profileedit

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
fun ProfileEditScreen(
    navController: NavController,
    viewModel: ProfileEditViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    GrowTopAppBar(
        text = "프로필 설정",
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
                value = "",
                onValueChange = {},
                hint = "상태메세지"
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