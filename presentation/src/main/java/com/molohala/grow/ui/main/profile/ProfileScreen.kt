package com.molohala.grow.ui.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.info.response.ProfileResponse
import com.molohala.grow.designsystem.color.GrowColor
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.applyCardView
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.legacy.rank.InfinityChartCell
import com.molohala.grow.designsystem.legacy.statcell.InfinityStatCell
import com.molohala.grow.designsystem.legacy.statcell.InfinityStatCellShimmer
import com.molohala.grow.designsystem.legacy.statcell.InfinityStatType
import com.molohala.grow.ui.main.main.NavGroup
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
        backgroundColor = GrowColor.background,
        text = "프로필"
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(GrowColor.background)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = scrollState
            ) {
                item {
                    val profile = uiAppState.profile as? FetchFlow.Success ?: return@item
                    Profile(profile = profile.data) {
                        navController.navigate(NavGroup.Setting.name)
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        uiAppState.githubFetchFlow.let {
                            when (it) {
                                is FetchFlow.Failure -> {
                                    InfinityStatCell(
                                        title = "커밋 개수",
                                        type = InfinityStatType.Baekjoon()
                                    ) {

                                    }
                                }

                                is FetchFlow.Fetching -> {
                                    InfinityStatCellShimmer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                }

                                is FetchFlow.Success -> {
                                    InfinityStatCell(
                                        modifier = Modifier
                                            .weight(1f),
                                        title = "커밋 개수",
                                        type = InfinityStatType.Github(commit = it.data?.totalCommits)
                                    ) {

                                    }
                                }
                            }
                        }
                        uiAppState.solvedac.let {
                            when (it) {
                                is FetchFlow.Failure -> {
                                    InfinityStatCell(
                                        title = "문제 푼 개수",
                                        type = InfinityStatType.Baekjoon()
                                    ) {

                                    }
                                }
                                is FetchFlow.Fetching -> {
                                    InfinityStatCellShimmer(
                                        modifier = Modifier
                                            .weight(1f)
                                    )
                                }
                                is FetchFlow.Success -> {
                                    InfinityStatCell(
                                        modifier = Modifier
                                            .weight(1f),
                                        title = "문제 푼 개수",
                                        type = InfinityStatType.Baekjoon(solved = it.data?.totalSolves)
                                    ) {

                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    InfinityChartCell {

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
fun Profile(
    profile: ProfileResponse,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .applyCardView(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = profile.name,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(GrowColor.background)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .bounceClick(onClick = onClick),
                text = "설정",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }

        Text(
            modifier = Modifier
                .padding(vertical = 24.dp),
            text = "\"뚝딱뚝딱\"",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun Logout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .applyCardView(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = "로그아웃",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
//        IconLogout()
    }
}