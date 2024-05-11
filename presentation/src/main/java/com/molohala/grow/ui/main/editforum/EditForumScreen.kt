package com.molohala.grow.ui.main.editforum

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
import com.molohala.grow.designsystem.component.button.ButtonType
import com.molohala.grow.designsystem.component.button.GrowTextButton
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.ui.main.createforum.CreateForumScreen
import com.molohala.grow.ui.main.createforum.EditForumSideEffect
import com.molohala.grow.ui.main.createforum.EditForumViewModel

@Composable
fun EditForumScreen(
    navController: NavController,
    viewModel: EditForumViewModel = viewModel(),
    forumId: Int
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchForum(forumId)
        viewModel.uiEffect.collect {
            when (it) {
                EditForumSideEffect.Failure -> {
                    Toast.makeText(context, "글작성 실패", Toast.LENGTH_SHORT).show()
                }

                EditForumSideEffect.Success -> {
                    navController.popBackStack()
                }
            }
        }
    }

    GrowTopAppBar(
        text = "글 수정",
        trailingContent = {
            GrowTextButton(
                text = "수정",
                type = ButtonType.Small,
                enabled = uiState.content.isNotEmpty(),
            ) {
                viewModel.editForum(forumId)
            }
        },
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        GrowTextField(
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