package com.molohala.grow.ui.main.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
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
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatar
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatarShimmer
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.shimmer.RowShimmer
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.language.MyLanguage
import com.bestswlkh0310.mydesignsystem.component.language.MyLanguageShimmer
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.molohala.grow.specific.chart.GrowChartCell
import com.molohala.grow.specific.statcell.GrowStatCell
import com.molohala.grow.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.specific.statcell.GrowStatType
import com.molohala.grow.specific.text.Headline
import com.molohala.grow.specific.chart.GrowChartCellShimmer
import com.molohala.grow.specific.text.LinkifyText
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

    MyTopAppBar(
        text = "프로필",
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
                    Info(uiAppState = uiAppState) {
                        navController.navigate(NavGroup.Setting.name)
                    }
                }
                item {
                    Bio(uiAppState = uiAppState) {
                        navController.navigate(NavGroup.ProfileEdit.name)
                    }
                }
                item {
                    Language(uiAppState = uiAppState)
                }
                item {
                    Statics(uiAppState = uiAppState)
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
    uiAppState: AppState,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        uiAppState.profile.let {
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
        MyIcon(
            modifier = Modifier
                .size(32.dp)
                .bounceClick(onClick = onClick),
            id = R.drawable.ic_setting,
            color = MyTheme.colorScheme.textAlt
        )
    }
}

@Composable
private fun Bio(
    uiAppState: AppState,
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
            MyIcon(
                modifier = Modifier
                    .size(20.dp)
                    .bounceClick(onClick = {
                        onClickEdit()
                    }),
                id = R.drawable.ic_write,
                color = MyTheme.colorScheme.textAlt
            )
        }
        val profile = (uiAppState.profile as? FetchFlow.Success)?.data
        profile?.let {
            val text = profile.bio.ifEmpty { "🤔" }
            SelectionContainer {
                LinkifyText(
                    text = text,
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
    uiAppState: AppState
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
            uiAppState.myLanguage.let {
                when (it) {
                    is FetchFlow.Failure -> {}
                    is FetchFlow.Fetching -> {
                        repeat(4) {
                            MyLanguageShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        if (it.data.isEmpty()) {
                            Text(
                                text = "🤔",
                                style = MyTheme.typography.bodyMedium,
                                color = MyTheme.colorScheme.textDarken
                            )
                        } else {
                            it.data.forEach { lang ->
                                MyLanguage(text = lang.name)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Statics(
    uiAppState: AppState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            modifier = Modifier
                .padding(start = 4.dp),
            text = "통계"
        )
        Stats(uiAppState = uiAppState)
        GithubChart(uiAppState = uiAppState)
        BaekjoonChart(uiAppState = uiAppState)
    }
}

@Composable
private fun Stats(
    uiAppState: AppState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val profile = uiAppState.profile as? FetchFlow.Success
        profile?.let {
            uiAppState.github.let {
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
            uiAppState.baekjoon.let {
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
    uiAppState: AppState
) {
    uiAppState.githubChartInfo.let {
        when (it) {
            is FetchFlow.Failure -> {}
            is FetchFlow.Fetching -> {
                GrowChartCellShimmer()
            }

            is FetchFlow.Success -> {
                it.data?.let { chart ->
                    GrowChartCell(chartInfo = chart) {

                    }
                }
            }
        }
    }
}

@Composable
private fun BaekjoonChart(
    uiAppState: AppState
) {
    uiAppState.baekjoonChartInfo.let {
        when (it) {
            is FetchFlow.Failure -> {}
            is FetchFlow.Fetching -> {
                GrowChartCellShimmer()
            }

            is FetchFlow.Success -> {
                it.data?.let { chart ->
                    GrowChartCell(chartInfo = chart) {

                    }
                }
            }
        }
    }
}