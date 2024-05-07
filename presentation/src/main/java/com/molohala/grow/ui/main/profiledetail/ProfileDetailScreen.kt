package com.molohala.grow.ui.main.profiledetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shimmer.RowShimmer
import com.molohala.grow.designsystem.specific.chart.GrowChartCell
import com.molohala.grow.designsystem.specific.chart.GrowChartCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatType
import com.molohala.grow.ui.main.main.NavGroup

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileDetailScreen(
    navController: NavController,
    viewModel: ProfileDetailViewModel = viewModel(),
    memberId: Int
) {

    val scrollState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = {
            viewModel.fetchProfile(memberId = memberId)
            viewModel.refresh()
        }
    )

    GrowTopAppBar(
        text = "프로필",
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = scrollState
            ) {
                item {
                    Info(uiState = uiState) {
                        navController.navigate(NavGroup.Setting.name)
                    }
                }
                item {
                    Stats(uiState = uiState)
                }
                item {
                    GithubChart(uiState = uiState)
                }
                item {
                    BaekjoonChart(uiState = uiState)
                }
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.isRefresh,
                state = pullRefreshState
            )
        }
    }
}

@Composable
private fun Info(
    uiState: ProfileDetailState,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.profile.let {
            when (it) {
                is FetchFlow.Failure -> {}

                is FetchFlow.Fetching -> {
                    GrowAvatarShimmer(type = AvatarType.ExtraLarge)
                    RowShimmer(width = 40.dp)
                    RowShimmer(width = 100.dp)
                }

                is FetchFlow.Success -> {
                    GrowAvatar(type = AvatarType.ExtraLarge)
                    Text(
                        text = it.data.name,
                        color = GrowTheme.colorScheme.textNormal,
                        style = GrowTheme.typography.bodyBold
                    )
                    Text(
                        text = "\"응아잇 안드로이드\"",
                        color = GrowTheme.colorScheme.textAlt,
                        style = GrowTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun Stats(
    uiState: ProfileDetailState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val profile = uiState.profile as? FetchFlow.Success
        profile?.let {
            uiState.github.let {
                when (it) {
                    is FetchFlow.Failure -> {}

                    is FetchFlow.Fetching -> {
                        GrowStatCellShimmer(modifier = Modifier.weight(1f))
                    }

                    is FetchFlow.Success -> {
                        GrowStatCell(
                            modifier = Modifier
                                .weight(1f),
                            label = "커밋 개수",
                            socialId = profile.data.getGithubId(),
                            type = GrowStatType.Github(commit = it.data?.totalCommits)
                        ) {

                        }
                    }
                }
            }
            uiState.baekjoon.let {
                when (it) {
                    is FetchFlow.Failure -> {}

                    is FetchFlow.Fetching -> {
                        GrowStatCellShimmer(modifier = Modifier.weight(1f))
                    }

                    is FetchFlow.Success -> {
                        GrowStatCell(
                            modifier = Modifier
                                .weight(1f),
                            label = "문제 푼 개수",
                            socialId = profile.data.getBaekjoonId(),
                            type = GrowStatType.Baekjoon(solved = it.data?.totalSolves)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GithubChart(
    uiState: ProfileDetailState
) {
    uiState.githubChartInfo.let {
        when (it) {
            is FetchFlow.Failure -> {}
            is FetchFlow.Fetching -> {
                GrowChartCellShimmer()
            }

            is FetchFlow.Success -> {
                GrowChartCell(chartInfo = it.data) {

                }
            }
        }
    }
}

@Composable
private fun BaekjoonChart(
    uiState: ProfileDetailState
) {
    uiState.baekjoonChartInfo.let {
        when (it) {
            is FetchFlow.Failure -> {}
            is FetchFlow.Fetching -> {
                GrowChartCellShimmer()
            }

            is FetchFlow.Success -> {
                GrowChartCell(chartInfo = it.data) {

                }
            }
        }
    }
}