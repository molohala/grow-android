package com.molohala.infinity.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.molohala.infinity.github.InfinityGithubRankCell
import com.molohala.infinity.typo.SubTitle
import com.molohala.infinity.typo.TopBar
import com.molohala.infinity.ui.main.statcell.InfinityStatCell
import com.molohala.infinity.ui.main.statcell.InfinityStatType

@Composable
fun HomeScreen(
    navController: NavController
) {
    TopBar(
        modifier = Modifier,
        backgroundColor = InfinityColor.background,
        text = "홈"
    ) {
        LazyColumn(
            modifier = Modifier
                .background(InfinityColor.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SubTitle(text = "iOS 개발자\n이강현님 환영합니다")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfinityStatCell(
                            modifier = Modifier.weight(1f),
                            title = "오늘 한 커밋 개수",
                            type = InfinityStatType.Github(commit = 7)
                        ) {

                        }
                        InfinityStatCell(
                            modifier = Modifier.weight(1f),
                            title = "오늘 푼 문제 개수",
                            type = InfinityStatType.Baekjoon(solved = 3)
                        ) {

                        }
                    }
                }

            }
            item {
                TodayGithub()
            }
            item {
                TodayBaekjoon()
            }
            item {
                WeekCommunity()
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun WeekCommunity() {

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
                InfinityCommunityCell {

                }
            }
        }
    }
}

@Composable
fun TodayGithub() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SubTitle(
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

@Composable
fun TodayBaekjoon() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SubTitle(
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