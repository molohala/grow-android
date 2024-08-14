package com.molohala.grow.ui.main.githubrank

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import com.bestswlkh0310.mydesignsystem.component.button.ButtonType
import com.bestswlkh0310.mydesignsystem.component.button.MyButton
import com.bestswlkh0310.mydesignsystem.component.button.MyTabButton
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.molohala.grow.common.util.updatedAt
import com.molohala.grow.specific.rank.GrowRankCell
import com.molohala.grow.specific.rank.GrowRankCellShimmer
import com.molohala.grow.ui.error.ErrorScreen
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

    MyTopAppBar(
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
                } else {
                    Indicator(
                        uiState = uiState,
                        selectedTab = uiState.selectedTab,
                        onClickTab = {
                            viewModel.clickTab(selectedTab = it)
                        }
                    )
                    uiState.githubRanksFetchFlow.let {
                        when (it) {
                            is FetchFlow.Failure -> {
                                ErrorScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }

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
                                    items(it.data.ranks) { rank ->
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier,
            text = "아직 Github ID를 설정하지 않았어요",
            color = MyTheme.colorScheme.textNormal,
            style = MyTheme.typography.bodyMedium
        )
        MyButton(
            text = "설정하기",
            type = ButtonType.Small
        ) {
            onClickSetting()
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun Indicator(
    uiState: GithubRankState,
    selectedTab: GithubRankTab,
    onClickTab: (GithubRankTab) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GithubRankTab.entries.forEach {
            MyTabButton(
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

        val data = uiState.githubRanksFetchFlow as? FetchFlow.Success
        data?.data?.updatedAt?.updatedAt()?.let {
            Text(
                text = it,
                style = MyTheme.typography.labelRegular,
                color = MyTheme.colorScheme.textAlt
            )
        }
    }
}