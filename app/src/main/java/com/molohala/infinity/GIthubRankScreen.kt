package com.molohala.infinity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun GithubRankScreen(
    navController: NavController,
    viewModel: GithubRankViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Title(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Github 랭킹"
        )
        GithubRankCell(
            rank = 1
        ) {

        }
    }
}