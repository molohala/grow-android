package com.molohala.infinity.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.molohala.infinity.color.InfinityColor
import com.molohala.infinity.commnuity.InfinityCommunityCell
import com.molohala.infinity.extension.applyCardView
import com.molohala.infinity.github.InfinityGithubRankCell
import com.molohala.infinity.typo.SubTitle
import com.molohala.infinity.typo.Title

@Composable
fun HomeScreen(
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .background(InfinityColor.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Title(text = "홈")
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SubTitle(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "이번주 인기글"
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(3) {
                        InfinityCommunityCell()
                    }
                }
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SubTitle(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "오늘의 Github Top 3"
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) {
                        InfinityGithubRankCell(
                            isCard = true,
                            rank = it + 1
                        ) {

                        }
                    }
                }
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SubTitle(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "오늘의 백준 Top 3"
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) {
                        InfinityGithubRankCell(
                            isCard = true,
                            rank = it + 1
                        ) {

                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 *         VStack(spacing: 12) {
 *             SubTitle("오늘의 백준 Top 3")
 *             VStack(spacing: 8) {
 *                 ForEach(0..<3, id: \.self) { i in
 *                     InfinityGithubRankCell(rank: i + 1) {
 *                         //
 *                     }
 *                     .cardView()
 *                 }
 *             }
 *         }
 *     }
 * }
 */