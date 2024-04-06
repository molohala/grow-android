package com.molohala.infinity.ui.main.profiledetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.baekjoon.InfinityBaekjoonCell
import com.molohala.infinity.github.InfinityGithubCell
import com.molohala.infinity.typo.Title

@Composable
fun ProfileDetailScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color(0xFFF4F5F9))
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Title(
            text = "노영재님의 프로필"
        ) {
            navController.popBackStack()
        }
        InfinityGithubCell {

        }
        InfinityBaekjoonCell {

        }
    }
}