package com.molohala.grow.ui.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.molohala.grow.R
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.foundation.shimmer.ShimmerRowBox
import com.molohala.grow.designsystem.specific.chart.GrowChartCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatType
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppState
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    profileViewModel: ProfileViewModel = viewModel()
) {

    val scrollState = rememberLazyListState()
    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by profileViewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = {
            appViewModel.fetchProfile()
            profileViewModel.refresh()
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
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = scrollState
            ) {
                item {
                    Info(uiAppState = uiAppState) {
                        navController.navigate(NavGroup.Setting.name)
                    }
                }
                item {
                    Stats(uiAppState = uiAppState)
                }
                item {
                    GrowChartCell {

                    }
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
    uiAppState: AppState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .applyCardView()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiAppState.profile.let {
                when (it) {
                    is FetchFlow.Failure -> {
                        Text(text = "불러오기 실패")
                    }
                    is FetchFlow.Fetching -> {
                        GrowAvatarShimmer(type = AvatarType.Large)
                        ShimmerRowBox(width = 40.dp)
                    }
                    is FetchFlow.Success -> {
                        GrowAvatar(type = AvatarType.Large)
                        Text(
                            text = it.data.name,
                            color = GrowTheme.colorScheme.textNormal,
                            style = GrowTheme.typography.bodyBold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        GrowIcon(
            modifier = Modifier
                .size(32.dp),
            id = R.drawable.ic_setting,
            color = GrowTheme.colorScheme.textAlt
        )
    }
}

@Composable
private fun Stats(
    uiAppState: AppState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        uiAppState.github.let {
            when (it) {
                is FetchFlow.Failure -> {
                    GrowStatCell(
                        label = "커밋 개수",
                        type = GrowStatType.Baekjoon()
                    ) {

                    }
                }

                is FetchFlow.Fetching -> {
                    GrowStatCellShimmer(modifier = Modifier.weight(1f))
                }

                is FetchFlow.Success -> {
                    GrowStatCell(
                        modifier = Modifier
                            .weight(1f),
                        label = "커밋 개수",
                        type = GrowStatType.Github(commit = it.data?.totalCommits)
                    ) {

                    }
                }
            }
        }
        uiAppState.baekjoon.let {
            when (it) {
                is FetchFlow.Failure -> {
                    GrowStatCell(
                        label = "문제 푼 개수",
                        type = GrowStatType.Baekjoon()
                    ) {

                    }
                }

                is FetchFlow.Fetching -> {
                    GrowStatCellShimmer(modifier = Modifier.weight(1f))
                }

                is FetchFlow.Success -> {
                    GrowStatCell(
                        modifier = Modifier
                            .weight(1f),
                        label = "문제 푼 개수",
                        type = GrowStatType.Baekjoon(solved = it.data?.totalSolves)
                    ) {

                    }
                }
            }
        }
    }
}