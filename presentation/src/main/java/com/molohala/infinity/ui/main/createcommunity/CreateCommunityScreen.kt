package com.molohala.infinity.ui.main.createcommunity

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.molohala.infinity.designsystem.InfinityTextField
import com.molohala.infinity.designsystem.color.InfinityColor
import com.molohala.infinity.designsystem.typo.TopBar
import com.molohala.infinity.extension.bounceClick

@Composable
fun CreateCommunityScreen(
    navController: NavController,
    viewModel: CreateCommunityViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect {
            when (it) {
                CreateCommunitySideEffect.Failure -> {
                    Toast.makeText(context, "글작성 실패", Toast.LENGTH_SHORT).show()
                }
                CreateCommunitySideEffect.Success -> {
                    navController.popBackStack()
                }
            }
        }
    }

    TopBar(
        text = "글쓰기",
        trailingContent = {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .bounceClick(onClick = {
                        viewModel.createCommunity()
                    }),
                text = "완료",
                style = MaterialTheme.typography.titleMedium,
                color = InfinityColor.blue
            )
        },
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        InfinityTextField(
            modifier = Modifier
                .height(300.dp)
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp),
            value = uiState.content,
            placeholder = "내용을 적어주세요",
            singleLine = false,
            onValueChange = viewModel::updateContent
        )
    }
}

@Preview
@Composable
fun CreateCommunityPreview() {
    CreateCommunityScreen(navController = rememberNavController())
}