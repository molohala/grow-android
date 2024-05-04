package com.molohala.grow.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.specific.commnuity.GrowCommunityCell
import com.molohala.grow.designsystem.specific.commnuity.GrowCommunityCellShimmer
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.legacy.baekjoon.InfinityBaekjoonRankCell
import com.molohala.grow.designsystem.legacy.baekjoon.InfinityBaekjoonRankCellShimmer
import com.molohala.grow.designsystem.legacy.rank.InfinityGithubRankCell
import com.molohala.grow.designsystem.legacy.rank.InfinityGithubRankCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatType
import com.molohala.grow.ui.root.AppViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    appViewModel: AppViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val uiAppState by appViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTodayGithubRank()
        viewModel.fetchTodayBaekjoonRank()
//        viewModel.fetchWeekCommunities()
    }

    GrowTopAppBar(
        modifier = Modifier,
        backgroundColor = GrowColor.background,
        text = "홈"
    ) {
        LazyColumn(
            modifier = Modifier
                .background(GrowColor.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiAppState.profile.let {
                        when (it) {
                            is FetchFlow.Fetching -> {
                                Text(text = "불러오기 실패")
                            }

                            is FetchFlow.Failure -> {

                            }

                            is FetchFlow.Success -> {
//                                SubTitle(text = "iOS 개발자\n${it.data.name}님 환영합니다")
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GrowStatCell(
                            modifier = Modifier.weight(1f),
                            label = "오늘 한 커밋 개수",
                            type = GrowStatType.Github(commit = 7)
                        ) {

                        }
                        GrowStatCell(
                            modifier = Modifier.weight(1f),
                            label = "오늘 푼 문제 개수",
                            type = GrowStatType.Baekjoon(solved = 3)
                        ) {

                        }
                    }
                }

            }
            item {
                TodayGithub(uiState = uiState)
            }
            item {
                TodayBaekjoon(uiState = uiState)
            }
            item {
                WeekCommunity(uiState = uiState)
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun WeekCommunity(
    uiState: HomeState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
//        SubTitle(
//            modifier = Modifier
//                .padding(top = 20.dp),
//            text = "이번주 인기글"
//        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            uiState.weekCommunities.let {
                when (it) {
                    is FetchFlow.Failure -> Text(text = "불러오기 실패")
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            GrowCommunityCellShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach {
                            GrowCommunityCell(
                                community = it,
                                onAppear = { /*TODO*/ }) {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodayGithub(uiState: HomeState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
//        SubTitle(
//            text = "오늘의 Github Top 3"
//        )
        Column(
            modifier = Modifier
                .applyCardView(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.todayGithubRanks.let {
                when (it) {
                    is FetchFlow.Failure -> Text(text = "불러오기 실패")
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            InfinityGithubRankCellShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach {
                            InfinityGithubRankCell(
                                rank = it
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodayBaekjoon(uiState: HomeState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
//        SubTitle(
//            text = "오늘의 백준 Top 3"
//        )
        Column(
            modifier = Modifier
                .applyCardView(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.todayBaekjoonRanks.let {
                when (it) {
                    is FetchFlow.Failure -> Text(text = "불러오기 실패")
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            InfinityBaekjoonRankCellShimmer()
                        }
                    }
                    is FetchFlow.Success -> {
                        it.data.forEach {
                            InfinityBaekjoonRankCell(rank = it) {

                            }
                        }
                    }
                }
            }
            repeat(3) {
//                InfinityGithubRankCell(
//                    rank = it + 1
//                ) {
//
//                }
            }
        }
    }
}