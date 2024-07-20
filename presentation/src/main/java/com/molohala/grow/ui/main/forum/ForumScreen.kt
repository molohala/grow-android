package com.molohala.grow.ui.main.forum

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bestswlkh0310.mydesignsystem.component.button.ButtonType
import com.bestswlkh0310.mydesignsystem.component.button.MyButton
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.molohala.grow.common.constant.Constant
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.specific.foum.GrowForumCell
import com.molohala.grow.specific.foum.GrowForumCellShimmer
import com.molohala.grow.ui.error.ErrorScreen
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: ForumViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val uiAppState by appViewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh,
        onRefresh = {
            viewModel.refresh()
        }
    )
    var showRemoveDialog by remember { mutableStateOf(false) }
    var showRemoveSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveFailureDialog by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var selectedForum by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchCommunities()
        viewModel.uiEffect.collect {
            when (it) {
                ForumSideEffect.RemoveForumFailure -> {
                    showRemoveFailureDialog = true
                }

                ForumSideEffect.RemoveForumSuccess -> {
                    showRemoveSuccessDialog = true
                }

                ForumSideEffect.ReportForumSuccess -> {
                    Toast.makeText(context, "신고 접수 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MyTopAppBar(
        backgroundColor = MyTheme.colorScheme.backgroundAlt,
        text = "포럼"
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            uiState.forums.let { state ->
                when (state) {
                    is FetchFlow.Failure -> {
                        ErrorScreen(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    is FetchFlow.Fetching -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            repeat(4) {
                                GrowForumCellShimmer()
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    is FetchFlow.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = scrollState
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            itemsIndexed(state.data) { idx, forum ->
                                val profile =
                                    uiAppState.profile as? FetchFlow.Success ?: return@itemsIndexed
                                GrowForumCell(
                                    forum = forum,
                                    onAppear = {
                                        state.data.firstOrNull { it.forum.forumId == forum.forum.forumId }
                                            ?: return@GrowForumCell
                                        val interval = Constant.pageInterval
                                        if (idx % interval == (interval - 1) && idx / interval == (state.data.size - 1) / interval) {
                                            viewModel.fetchNextCommunities()
                                        }
                                    },
                                    onRemove = {
                                        selectedForum = forum.forum.forumId
                                        showRemoveDialog = true
                                    },
                                    onEdit = {
                                        navController.navigate("${NavGroup.EditForum.name}/${forum.forum.forumId}")
                                    },
                                    profileId = profile.data.id,
                                    onClickLike = {
                                        viewModel.patchLike(forum.forum.forumId)
                                    },
                                    onReport = {
                                        viewModel.updateReportForum(forum)
                                        showReportDialog = true
                                    }
                                ) {
                                    navController.navigate("${NavGroup.ForumDetail.name}/${forum.forum.forumId}")
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
                MyButton(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    text = "글쓰기",
                    type = ButtonType.Large,
                    leftIcon = R.drawable.ic_write,
                    shape = CircleShape
                ) {
                    navController.navigate(NavGroup.CreateForum.name)
                }
                Spacer(modifier = Modifier.height(92.dp))
            }
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

    if (showReportDialog) {
        MyDialog(
            title = "신고 내용을 적어 주세요",
            onSuccessRequest = {
                if (uiState.reportCommentReason.isEmpty()) {
                    Toast.makeText(context, "신고 내용을 적어 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    showReportDialog = false
                    viewModel.reportForum()
                }
            },
            onCancelRequest = {
                showReportDialog = false
            },
            onDismissRequest = {
                showReportDialog = false
            }
        ) {
            MyTextField(
                value = uiState.reportCommentReason,
                onValueChange = viewModel::updateReportCommentReason
            )
        }
    }
}
