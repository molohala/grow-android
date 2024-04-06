package com.molohala.infinity.ui.main.githubrank

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.infinity.GithubRankCell
import com.molohala.infinity.InfinityButton
import com.molohala.infinity.Title

@Composable
fun GithubRankScreen(
    navController: NavController,
    viewModel: GithubRankViewModel = viewModel()
) {

    val tempRankings = Array(10) { it }

    LazyColumn(
        modifier = Modifier
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            com.molohala.infinity.Title(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Github 랭킹"
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .border(
                        width = 1.5.dp,
                        color = Color.LightGray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10)
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "아직 Github 설정이 완료되지 않았어요",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Github Id를 설정하고 순위권 도전해 보세요!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                InfinityButton(
                    modifier = Modifier
                        .width(110.dp)
                        .height(40.dp),
                    text = "설정하기"
                ) {
                    navController.navigate("profile_detail")
                }
            }
        }
        items(tempRankings) {
            com.molohala.infinity.GithubRankCell(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                rank = it + 1
            ) {
                navController.navigate("profile_detail")
            }
        }
    }
}