package com.molohala.grow.ui.main.githubrank

import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.button.ButtonType
import com.molohala.grow.designsystem.component.button.GrowButton
import com.molohala.grow.designsystem.component.button.GrowTabButton
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.specific.rank.GrowRankCell
import com.molohala.grow.designsystem.specific.rank.GrowRankCellShimmer
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GithubRankScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: GithubRankViewModel = viewModel()
) {

    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh, onRefresh = viewModel::refresh
    )

    LaunchedEffect(Unit) {
        viewModel.fetchGithubRank()
    }

    GrowTopAppBar(
        text = "Github 랭킹"
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                val github = uiAppState.github as? FetchFlow.Success ?: return@Column
                if (github.data == null) {
                    RecommendingSettingGithub(onClickSetting = {
                        navController.navigate(NavGroup.GithubSetting.name)
                    })
                }
                Indicator(
                    selectedTab = uiState.selectedTab,
                    onClickTab = {
                        viewModel.clickTab(selectedTab = it)
                    }
                )
                uiState.githubRanksFetchFlow.let {
                    when (it) {
                        is FetchFlow.Failure -> {}

                        is FetchFlow.Fetching -> {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                repeat(4) {
                                    GrowRankCellShimmer()
                                }
                            }
                        }

                        is FetchFlow.Success -> {
                            LazyColumn(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                state = scrollState
                            ) {
                                items(it.data) { rank ->
                                    GrowRankCell(
                                        name = rank.memberName,
                                        socialId = rank.socialId,
                                        rank = rank.rank,
                                        label = "${rank.count} 커밋"
                                    ) {
                                        navController.navigate("${NavGroup.ProfileDetail.name}/${rank.memberId}")
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(64.dp))
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.isRefresh, state = pullRefreshState
            )
        }
    }
}

@Composable
private fun RecommendingSettingGithub(
    onClickSetting: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = GrowTheme.colorScheme.textAlt,
                shape = RoundedCornerShape(12)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = "아직 Github ID를 설정하지 않았어요",
            color = GrowTheme.colorScheme.textNormal,
            style = GrowTheme.typography.bodyMedium
        )
        GrowButton(
            text = "설정하기",
            type = ButtonType.Small
        ) {
            onClickSetting()
        }
    }
}

@Composable
private fun Indicator(
    selectedTab: GithubRankTab, onClickTab: (GithubRankTab) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GithubRankTab.entries.forEach {
            GrowTabButton(
                modifier = Modifier,
                text = it.label,
                selected = it == selectedTab,
                type = ButtonType.Small,
                shape = CircleShape
            ) {
                onClickTab(it)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}