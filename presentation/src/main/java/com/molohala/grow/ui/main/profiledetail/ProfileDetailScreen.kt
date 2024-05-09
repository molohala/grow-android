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
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.component.avatar.GrowAvatarShimmer
import com.molohala.grow.designsystem.component.language.GrowLanguage
import com.molohala.grow.designsystem.component.language.GrowLanguageShimmer
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shimmer.RowShimmer
import com.molohala.grow.designsystem.specific.chart.GrowChartCell
import com.molohala.grow.designsystem.specific.chart.GrowChartCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatCell
import com.molohala.grow.designsystem.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.designsystem.specific.statcell.GrowStatType
import com.molohala.grow.designsystem.specific.text.Headline
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
    GrowTopAppBar(
        text = "${name}님의 프로필",
        onClickBackButton = {
            navController.popBackStack()
        },
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
                    GrowAvatarShimmer(type = AvatarType.ExtraLarge)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        RowShimmer(width = 60.dp)
                        RowShimmer(width = 40.dp)
                    }
                }

                is FetchFlow.Success -> {
                    GrowAvatar(type = AvatarType.ExtraLarge)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "${it.data.job} 개발자",
                            color = GrowTheme.colorScheme.textDarken,
                            style = GrowTheme.typography.labelRegular
                        )
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
            Text(
                text = profile.bio,
                style = GrowTheme.typography.bodyMedium,
                color = GrowTheme.colorScheme.textDarken
            )
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
                            GrowLanguageShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach { lang ->
                            GrowLanguage(text = lang.name)
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