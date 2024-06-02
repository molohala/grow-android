package com.molohala.grow.ui.main.createforum

import android.widget.Toast
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bestswlkh0310.mydesignsystem.component.button.ButtonType
import com.bestswlkh0310.mydesignsystem.component.button.MyTextButton
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.molohala.grow.ui.main.editforum.CreateForumSideEffect
import com.molohala.grow.ui.main.editforum.CreateForumViewModel

@Composable
fun CreateForumScreen(
    navController: NavController,
    viewModel: CreateForumViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                CreateForumSideEffect.Failure -> {
                    Toast.makeText(context, "글작성 실패", Toast.LENGTH_SHORT).show()
                }

                CreateForumSideEffect.Success -> {
                    navController.popBackStack()
                }
            }
        }
    }

    MyTopAppBar(
        text = "글쓰기",
        trailingContent = {
            MyTextButton(
                text = "완료",
                type = ButtonType.Small,
                enabled = uiState.content.isNotEmpty(),
            ) {
                viewModel.createForum()
            }
        },
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        MyTextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 300.dp)
                .padding(12.dp),
            value = uiState.content,
            hint = "내용을 적어주세요",
            singleLine = false,
            includeIcon = false,
            onValueChange = viewModel::updateContent
        )
    }
}

@Preview
@Composable
fun CreateForumPreview() {
    CreateForumScreen(navController = rememberNavController())
}