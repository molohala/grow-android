package com.molohala.grow.ui.main.githubrank

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.button.GrowCTAButton
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.legacy.selector.InfinitySelector
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
        refreshing = uiState.isRefresh,
        onRefresh = viewModel::refresh
    )

    LaunchedEffect(Unit) {
        viewModel.fetchGithubRank()
    }

    GrowTopAppBar(
        text = "Github 랭킹",
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val github = uiAppState.githubFetchFlow as? FetchFlow.Success ?: return@Column
                    if (github.data == null) {
                        RecommendingSettingGithub(onClickSetting = {
                            navController.navigate(NavGroup.GithubSetting.name)
                        })
                    }
                    Indicator(selectedTab = uiState.selectedTab, onClickTab = {
                        viewModel.clickTab(selectedTab = it)
                    })
                }
                uiState.githubRanksFetchFlow.let {
                    when (it) {
                        is FetchFlow.Failure -> {
                            Text(text = "불러오기 실패")
                        }

                        is FetchFlow.Fetching -> {
                            Column {
                                repeat(4) {
                                    GrowRankCellShimmer(
                                        modifier = Modifier
                                            .padding(horizontal = 20.dp)
                                    )
                                }
                            }
                        }

                        is FetchFlow.Success -> {
                            LazyColumn(
                                modifier = Modifier
                                    .background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                state = scrollState
                            ) {
                                items(it.data) { rank ->
                                    GrowRankCell(
                                        name = rank.memberName,
                                        socialId = rank.socialId,
                                        rank = rank.rank,
                                        label = "${rank.count} 문제"
                                    ) {
                                        navController.navigate("profile_detail")
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                            }
                        }
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
fun RecommendingSettingGithub(
    onClickSetting: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
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
        GrowCTAButton(
            modifier = Modifier
                .width(110.dp)
                .height(40.dp)
                .padding(top = 4.dp),
            text = "설정하기"
        ) {
            onClickSetting()
        }
    }
}

@Composable
fun Indicator(
    selectedTab: GithubRankTab,
    onClickTab: (GithubRankTab) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GithubRankTab.entries.forEach {
            InfinitySelector(
                text = it.label,
                isSelected = selectedTab == it
            ) {
                onClickTab(it)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}