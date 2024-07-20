package com.molohala.grow.ui.main.profiledetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
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
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatar
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatarShimmer
import com.bestswlkh0310.mydesignsystem.component.language.MyLanguage
import com.bestswlkh0310.mydesignsystem.component.language.MyLanguageShimmer
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.shimmer.RowShimmer
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.specific.chart.GrowChartCell
import com.molohala.grow.specific.chart.GrowChartCellShimmer
import com.molohala.grow.specific.statcell.GrowStatCell
import com.molohala.grow.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.specific.statcell.GrowStatType
import com.molohala.grow.specific.text.Headline
import com.molohala.grow.specific.text.LinkifyText
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
            viewModel.fetchProfile(memberId)
            viewModel.refresh()
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchProfile(memberId)
    }

    val profile = (uiState.profile as? FetchFlow.Success)?.data
    val name = profile?.name ?: ""
    MyTopAppBar(
        text = "${name}님의 프로필",
        onClickBackButton = {
            navController.popBackStack()
        },
        backgroundColor = MyTheme.colorScheme.backgroundAlt,
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
                verticalArrangement = Arrangement.spacedBy(28.dp),
                state = scrollState
            ) {
                item {
                    Info(uiState = uiState) {
                        navController.navigate(NavGroup.Setting.name)
                    }
                }
                item {
                    Bio(uiState = uiState) {
                        navController.navigate(NavGroup.ProfileEdit.name)
                    }
                }
                item {
                    Language(uiState = uiState)
                }
                item {
                    Statics(uiState = uiState)
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uiState.profile.let {
            when (it) {
                is FetchFlow.Failure -> {}
                is FetchFlow.Fetching -> {
                    MyAvatarShimmer(type = AvatarType.ExtraLarge)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        RowShimmer(width = 60.dp)
                        RowShimmer(width = 40.dp)
                    }
                }

                is FetchFlow.Success -> {
                    MyAvatar(type = AvatarType.ExtraLarge)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        val isDesigner = it.data.job == "Designer"
                        Text(
                            text = "${it.data.job} ${if (isDesigner) "" else "개발자"}",
                            color = MyTheme.colorScheme.textDarken,
                            style = MyTheme.typography.labelRegular
                        )
                        Text(
                            text = it.data.name,
                            color = MyTheme.colorScheme.textNormal,
                            style = MyTheme.typography.bodyBold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun Bio(
    uiState: ProfileDetailState,
    onClickEdit: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Headline(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = "소개글"
            )
        }
        val profile = (uiState.profile as? FetchFlow.Success)?.data
        profile?.let {
            SelectionContainer {
                LinkifyText(
                    text = profile.bio,
                    style = MyTheme.typography.bodyMedium,
                    color = MyTheme.colorScheme.textDarken
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Language(
    uiState: ProfileDetailState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            modifier = Modifier
                .padding(start = 4.dp),
            text = "사용 언어"
        )
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.myLanguage.let {
                when (it) {
                    is FetchFlow.Failure -> {}
                    is FetchFlow.Fetching -> {
                        repeat(4) {
                            MyLanguageShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach { lang ->
                            MyLanguage(text = lang.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Statics(
    uiState: ProfileDetailState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            modifier = Modifier
                .padding(start = 4.dp),
            text = "통계"
        )
        Stats(uiState = uiState)
        GithubChart(uiState = uiState)
        BaekjoonChart(uiState = uiState)
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