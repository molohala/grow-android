package com.molohala.grow.ui.main.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.molohala.grow.R
import com.molohala.grow.common.constant.Constant
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.designsystem.component.button.ButtonType
import com.molohala.grow.designsystem.component.button.GrowButton
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.specific.foum.GrowForumCell
import com.molohala.grow.designsystem.specific.foum.GrowForumCellShimmer
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    forumViewModel: ForumViewModel = viewModel()
) {
    val uiState by forumViewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = {
            forumViewModel.refresh()
        }
    )

    LaunchedEffect(Unit) {
        forumViewModel.fetchCommunities()
    }

    GrowTopAppBar(
        backgroundColor = GrowTheme.colorScheme.backgroundAlt,
        text = "포럼"
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            uiState.communities.let {
                when (it) {
                    is FetchFlow.Failure -> {
                        Text(text = "불러오기 실패")
                    }

                    is FetchFlow.Fetching -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(4) {
                                GrowForumCellShimmer()
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    is FetchFlow.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = scrollState
                        ) {
                            itemsIndexed(it.data) { idx, forum ->
                                GrowForumCell(forum = forum, onAppear = {
                                    it.data.firstOrNull { it.forum.forumId == forum.forum.forumId }
                                        ?: return@GrowForumCell
                                    val interval = Constant.pageInterval
                                    if (idx % interval == (interval - 1) && idx / interval == (it.data.size - 1) / interval) {
                                        forumViewModel.fetchNextCommunities()
                                    }
                                }) {
                                    navController.navigate(NavGroup.ForumDetail.name)
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(64.dp))
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.isRefresh,
                state = pullRefreshState
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                GrowButton(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    text = "글쓰기",
                    type = ButtonType.Large,
                    leftIcon = R.drawable.ic_write,
                    shape = CircleShape
                ) {

                }
                Spacer(modifier = Modifier.height(92.dp))
            }
        }
    }
}
