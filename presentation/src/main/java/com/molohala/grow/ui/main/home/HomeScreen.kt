package com.molohala.grow.ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.common.flow.FetchFlow
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.extension.applyCardView
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.shimmer.RowShimmer
import com.molohala.grow.specific.foum.GrowForumCell
import com.molohala.grow.specific.foum.GrowForumCellShimmer
import com.molohala.grow.specific.rank.GrowRankCell
import com.molohala.grow.specific.rank.GrowRankCellShimmer
import com.molohala.grow.specific.statcell.GrowStatCell
import com.molohala.grow.specific.statcell.GrowStatCellShimmer
import com.molohala.grow.specific.statcell.GrowStatType
import com.molohala.grow.specific.text.Headline
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppState
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    appViewModel: AppViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val uiAppState by appViewModel.uiState.collectAsState()
    var selectedForum by remember { mutableStateOf<Int?>(null) }
    var showRemoveSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveFailureDialog by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = {
            viewModel.refresh()
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchTodayGithubRank()
        viewModel.fetchTodayBaekjoonRank()
        viewModel.fetchWeekCommunities()
        viewModel.uiEffect.collect {
            when (it) {
                HomeSideEffect.RemoveForumFailure -> {
                    showRemoveFailureDialog = true
                }

                HomeSideEffect.RemoveForumSuccess -> {
                    showRemoveSuccessDialog = true
                }
            }
        }
    }

    MyTopAppBar(
        backgroundColor = MyTheme.colorScheme.backgroundAlt,
        text = "홈"
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                state = scrollState
            ) {
                item {
                    Greeting(uiAppState = uiAppState)
                }
                item {
                    Stat(uiAppState = uiAppState)
                }
                item {
                    TodayGithub(uiState = uiState) {
                        navController.navigate("${NavGroup.ProfileDetail.name}/${it}")
                    }
                }
                item {
                    TodayBaekjoon(uiState = uiState) {
                        navController.navigate("${NavGroup.ProfileDetail.name}/${it}")
                    }
                }
                item {
                    WeekForum(
                        uiState = uiState,
                        onRemoveForum = {
                            selectedForum = it
                            showRemoveDialog = true
                        },
                        onEditForum = {
                            navController.navigate("${NavGroup.EditForum.name}/${it}")
                        },
                        uiAppState = uiAppState,
                        onClick = {
                            navController.navigate("${NavGroup.ForumDetail.name}/${it}")
                        },
                        onClickLike = {
                            viewModel.patchLike(it)
                        }
                    )
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

    if (showRemoveDialog) {
        MyDialog(
            title = "정말 게시글을 삭제하시겠습니까?",
            successText = "삭제하기",
            cancelText = "아니요",
            onCancelRequest = {
                showRemoveDialog = false
            },
            onDismissRequest = {
                showRemoveDialog = false
            },
            onSuccessRequest = {
                showRemoveDialog = false
                val selectedForum = selectedForum ?: return@MyDialog
                viewModel.removeForum(forumId = selectedForum)
            }
        )
    }

    if (showRemoveSuccessDialog) {
        MyDialog(
            title = "게시글 삭제 성공",
            onDismissRequest = {
                showRemoveSuccessDialog = false
            },
        )
    }

    if (showRemoveFailureDialog) {
        MyDialog(
            title = "게시글 삭제 실패",
            onDismissRequest = {
                showRemoveFailureDialog = false
            },
        )
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
                        RowShimmer(width = 80.dp)
                        RowShimmer(width = 130.dp)
                    }
                }

                is FetchFlow.Failure -> {}

                is FetchFlow.Success -> {
                    Column {
                        val isDeveloper = it.data.job.isEmpty()
                        val isDesigner = it.data.job == "Designer"
                        Headline(text = "${it.data.job}${if (isDeveloper) "" else " "}${if (isDesigner) "" else "개발자"}")
                        Headline(text = "${it.data.name}님 환영합니다")
                    }
                }
            }
        }
    }

}

@Composable
private fun Stat(
    uiAppState: AppState
) {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val profile = uiAppState.profile as? FetchFlow.Success
        profile?.let { profile ->
            uiAppState.github.let {
                when (it) {
                    is FetchFlow.Failure -> {}

                    is FetchFlow.Fetching -> {
                        GrowStatCellShimmer(
                            modifier = Modifier.weight(1f)
                        )
                    }

                    is FetchFlow.Success -> {
                        GrowStatCell(
                            modifier = Modifier.weight(1f),
                            label = "오늘 한 커밋 개수",
                            socialId = profile.data.getGithubId(),
                            type = GrowStatType.Github(commit = it.data?.todayCommits?.contributionCount),

                            ) {

                        }
                    }
                }
            }
            uiAppState.baekjoon.let {
                when (it) {
                    is FetchFlow.Failure -> {}

                    is FetchFlow.Fetching -> {
                        GrowStatCellShimmer(
                            modifier = Modifier.weight(1f)
                        )
                    }

                    is FetchFlow.Success -> {
                        GrowStatCell(
                            modifier = Modifier.weight(1f),
                            label = "오늘 푼 문제 개수",
                            socialId = profile.data.getGithubId(),
                            type = GrowStatType.Baekjoon(it.data?.todaySolves?.solvedCount)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodayGithub(
    uiState: HomeState,
    onClickMember: (memberId: Int) -> Unit
) {
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
                    is FetchFlow.Failure -> {}

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
                                onClickMember(rank.memberId)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodayBaekjoon(
    uiState: HomeState,
    onClickMember: (memberId: Int) -> Unit
) {
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
                    is FetchFlow.Failure -> {}

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
                                onClickMember(rank.memberId)
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
    uiState: HomeState,
    uiAppState: AppState,
    onRemoveForum: (forumId: Int) -> Unit,
    onEditForum: (forumId: Int) -> Unit,
    onClickLike: (forumId: Int) -> Unit,
    onClick: (forumId: Int) -> Unit
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
            uiState.weekForums.let {
                when (it) {
                    is FetchFlow.Failure -> {}

                    is FetchFlow.Fetching -> {
                        repeat(3) {
                            GrowForumCellShimmer()
                        }
                    }

                    is FetchFlow.Success -> {
                        it.data.forEach { forum ->
                            val forumId = forum.forum.forumId
                            val profile = uiAppState.profile as? FetchFlow.Success ?: return@forEach
                            GrowForumCell(
                                forum = forum,
                                onAppear = { /*TODO*/ },
                                onRemove = {
                                    onRemoveForum(forumId)
                                },
                                onEdit = {
                                    onEditForum(forumId)
                                },
                                profileId = profile.data.id,
                                onClickLike = {
                                    onClickLike(forumId)
                                }
                            ) {
                                onClick(forum.forum.forumId)
                            }
                        }
                    }
                }
            }
        }
    }
}
