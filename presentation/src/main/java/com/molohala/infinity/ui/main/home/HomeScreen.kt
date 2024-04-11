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
import com.molohala.infinity.github.InfinityGithubRankCell
import com.molohala.infinity.typo.SubTitle
import com.molohala.infinity.typo.TopBar

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
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
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
                InfinityCommunityCell()
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

@Composable
fun TodayBaekjoon() {
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