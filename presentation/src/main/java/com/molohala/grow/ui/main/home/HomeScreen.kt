package com.molohala.grow.ui.main.home

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
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shimmer.ShimmerRowBox
import com.molohala.grow.designsystem.specific.foum.GrowForumCell
import com.molohala.grow.designsystem.specific.foum.GrowForumCellShimmer
import com.molohala.grow.designsystem.specific.rank.GrowRankCell
import com.molohala.grow.designsystem.specific.rank.GrowRankCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatType
import com.molohala.grow.designsystem.specific.text.Headline
import com.molohala.grow.ui.root.AppState
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
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
        text = "홈"
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            item {
                Greeting(uiAppState = uiAppState)
            }
            item {
                Stat(uiAppState = uiAppState)
            }
            item {
                TodayGithub(uiState = uiState)
            }
            item {
                TodayBaekjoon(uiState = uiState)
            }
            item {
                WeekForum(uiState = uiState)
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun Greeting(uiAppState: AppState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        uiAppState.profile.let {
            when (it) {
                is FetchFlow.Fetching -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ShimmerRowBox(width = 80.dp)
                        ShimmerRowBox(width = 130.dp)
                    }
                }
                is FetchFlow.Failure -> {
                    Text(text = "불러오기 실패")
                }
                is FetchFlow.Success -> {
                    Column {
                        Headline(text = "iOS 개발자")
                        Headline(text = "${it.data.name}님 환영합니다")
                    }
                }
            }
        }
    }
}

@Composable
private fun Stat(uiAppState: AppState) {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        uiAppState.github.let {
            when (it) {
                is FetchFlow.Failure -> {
                    Text(text = "불러오기 실패")
                }
                is FetchFlow.Fetching -> {
                    GrowStatCellShimmer(
                        modifier = Modifier.weight(1f)
                    )
                }
                is FetchFlow.Success -> {
                    GrowStatCell(
                        modifier = Modifier.weight(1f),
                        label = "오늘 한 커밋 개수",
                        type = GrowStatType.Github(commit = it.data?.todayCommits?.contributionCount)
                    ) {

                    }
                }
            }
        }
        uiAppState.baekjoon.let {
            when (it) {
                is FetchFlow.Failure -> {
                    Text(text = "불러오기 실패")
                }
                is FetchFlow.Fetching -> {
                    GrowStatCellShimmer(
                        modifier = Modifier.weight(1f)
                    )
                }
                is FetchFlow.Success -> {
                    GrowStatCell(
                        modifier = Modifier.weight(1f),
                        label = "오늘 푼 문제 개수",
                        type = GrowStatType.Baekjoon(it.data?.todaySolves?.solvedCount)
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun TodayGithub(uiState: HomeState) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Headline(text = "오늘의 Github Top 3")
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .applyCardView()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            uiState.todayGithubRanks.let {
                when (it) {
                    is FetchFlow.Failure -> {
                        Text(text = "불러오기 실패")
                    }
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            GrowRankCellShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach { rank ->
                            GrowRankCell(
                                name = rank.memberName,
                                socialId = rank.socialId,
                                rank = rank.rank,
                                label = "${rank.count} 커밋"
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
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Headline(text = "오늘의 백준 Top 3")
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .applyCardView()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            uiState.todayBaekjoonRanks.let {
                when (it) {
                    is FetchFlow.Failure -> {
                        Text(text = "불러오기 실패")
                    }
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            GrowRankCellShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach { rank ->
                            GrowRankCell(
                                name = rank.memberName,
                                socialId = rank.socialId,
                                rank = rank.rank,
                                label = "${rank.count} 문제"
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
fun WeekForum(
    uiState: HomeState
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Headline(text = "이번주 인기글")
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.weekCommunities.let {
                when (it) {
                    is FetchFlow.Failure -> {
                        Text(text = "불러오기 실패")
                    }
                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            GrowForumCellShimmer()
                        }
                    }
                    is FetchFlow.Success -> {
                        it.data.forEach { forum ->
                            GrowForumCell(
                                forum = forum,
                                onAppear = { /*TODO*/ }) {

                            }
                        }
                    }
                }
            }
        }
    }
}
