package com.molohala.grow.ui.main.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.molohala.grow.R
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.common.util.timeAgo
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.data.forum.response.ForumContentResponse
import com.molohala.grow.designsystem.component.avatar.AvatarType
import com.molohala.grow.designsystem.component.avatar.GrowAvatar
import com.molohala.grow.designsystem.component.button.GrowLikeButton
import com.molohala.grow.designsystem.component.dialog.GrowDialog
import com.molohala.grow.designsystem.component.divider.GrowDivider
import com.molohala.grow.designsystem.component.menu.GrowMenu
import com.molohala.grow.designsystem.component.menu.GrowMenuData
import com.molohala.grow.designsystem.component.menu.MenuType
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.specific.comment.GrowCommentCell
import com.molohala.grow.designsystem.specific.comment.GrowCommentCellShimmer
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumDetailScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: ForumDetailViewModel = viewModel(),
    forumId: Int
) {

    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh, onRefresh = {
            viewModel.refresh(forumId)
        }
    )
    var showRemoveForumDialog by remember { mutableStateOf(false) }
    var showRemoveForumSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveForumFailureDialog by remember { mutableStateOf(false) }
    var showRemoveCommentSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveCommentFailureDialog by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchForum(forumId)
        viewModel.fetchComments(forumId)
        viewModel.uiEffect.collect {
            when (it) {
                ForumDetailSideEffect.CreateCommentFailure -> {}
                ForumDetailSideEffect.CreateCommentSuccess -> {}
                ForumDetailSideEffect.RemoveCommentFailure -> {
                    showRemoveCommentFailureDialog = true
                }
                ForumDetailSideEffect.RemoveCommentSuccess -> {
                    showRemoveCommentSuccessDialog = true
                }
                ForumDetailSideEffect.RemoveForumFailure -> {
                    showRemoveForumFailureDialog = true
                }
                ForumDetailSideEffect.RemoveForumSuccess -> {
                    showRemoveForumSuccessDialog = true
                }
            }
        }
    }

    GrowTopAppBar(
        text = "",
        onClickBackButton = {
            navController.popBackStack()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn {
                item {
                    uiState.forum.let {
                        when (it) {
                            is FetchFlow.Failure -> {
                                Text(text = "불러오기 실패")
                            }

                            is FetchFlow.Fetching -> {
                                ForumDetailCellShimmer()
                            }

                            is FetchFlow.Success -> {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Forum(
                                        forum = it.data,
                                        onClickLike = {
                                            viewModel.patchLike(forumId)
                                        },
                                        isMenuExpanded = isMenuExpanded,
                                        onChangeIsMenuExpanded = {
                                            isMenuExpanded = it
                                        },
                                        onEdit = {
                                            navController.navigate("${NavGroup.EditForum.name}/${forumId}")
                                        },
                                        onRemove = {
                                            showRemoveForumDialog = true
                                        }
                                    )
                                    GrowDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                    Comments(uiState = uiState) {

                                    }
                                    Spacer(modifier = Modifier.height(64.dp))
                                }
                            }
                        }
                    }
                }
            }
            val isIconEnable = uiState.currentComment.isNotEmpty()
            val iconColor = if (isIconEnable) {
                GrowTheme.colorScheme.textFieldPrimary
            } else {
                GrowTheme.colorScheme.textFieldTextDisabled
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val shape = RoundedCornerShape(24.dp)
                GrowTextField(
                    modifier = Modifier
                        .clip(shape)
                        .background(GrowTheme.colorScheme.background)
                        .weight(1f),
                    value = uiState.currentComment,
                    onValueChange = viewModel::updateCurrentComment,
                    shape = shape,
                    trailingIcon = {},
                    singleLine = false
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                ) {
                    GrowIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .bounceClick(onClick = {
                                viewModel.createComment(forumId)
                            }),
                        id = R.drawable.ic_send,
                        color = iconColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                refreshing = uiState.isRefresh,
                state = pullRefreshState
            )
        }
    }

    if (showRemoveForumDialog) {
        GrowDialog(
            title = "정말 게시글을 삭제하시겠습니까?",
            successText = "삭제하기",
            cancelText = "아니요",
            onCancelRequest = {
                showRemoveForumDialog = false
            },
            onDismissRequest = {
                showRemoveForumDialog = false
            },
            onSuccessRequest = {
                showRemoveForumDialog = false
                viewModel.removeForum(forumId = forumId)
            }
        )
    }

    if (showRemoveForumSuccessDialog) {
        GrowDialog(
            title = "게시글 삭제 성공",
            onDismissRequest = {
                navController.popBackStack()
                showRemoveForumSuccessDialog = false
            },
        )
    }

    if (showRemoveForumFailureDialog) {
        GrowDialog(
            title = "게시글 삭제 실패",
            onDismissRequest = {
                showRemoveForumFailureDialog = false
            },
        )
    }

    if (showRemoveCommentSuccessDialog) {
        GrowDialog(
            title = "댓글 삭제 성공",
            onDismissRequest = {
                showRemoveCommentSuccessDialog = false
            },
        )
    }

    if (showRemoveCommentFailureDialog) {
        GrowDialog(
            title = "댓글 삭제 실패",
            onDismissRequest = {
                showRemoveCommentFailureDialog = false
            },
        )
    }
}

@Composable
private fun Forum(
    isMenuExpanded: Boolean,
    onChangeIsMenuExpanded: (Boolean) -> Unit,
    forum: ForumContentResponse,
    onClickLike: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GrowAvatar(type = AvatarType.Medium)
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = forum.writerName,
                    style = GrowTheme.typography.bodyBold,
                    color = GrowTheme.colorScheme.textNormal
                )
                Text(
                    text = forum.createdAt.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = GrowTheme.colorScheme.textAlt
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                GrowIcon(
                    modifier = Modifier.bounceClick(onClick = {
                        onChangeIsMenuExpanded(true)
                    }),
                    id = R.drawable.ic_detail_vertical,
                    color = GrowTheme.colorScheme.textAlt
                )
                GrowMenu(
                    expanded = isMenuExpanded,
                    menuList = listOf(
                        GrowMenuData("수정하기", onClick = onEdit),
                        GrowMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                    ),
                    onDismissRequest = { onChangeIsMenuExpanded(false) }
                )
            }
        }
        Text(
            text = forum.content,
            style = GrowTheme.typography.bodyRegular,
            color = GrowTheme.colorScheme.textNormal
        )
        GrowLikeButton(
            like = forum.like,
            enabled = forum.liked,
            onClick = onClickLike
        )
    }
}

@Composable
private fun Comments(
    uiState: ForumDetailState,
    onClickRemoveComment: (CommentResponse) -> Unit
) {
    uiState.comments.let {
        when (it) {
            is FetchFlow.Failure -> {
                Text(text = "불러오기 실패")
            }

            is FetchFlow.Fetching -> {
                GrowCommentCellShimmer()
            }

            is FetchFlow.Success -> {
                Column {
                    it.data.forEach { comment ->
                        GrowCommentCell(comment = comment) {
                            onClickRemoveComment(comment)
                        }
                    }
                }
            }
        }
    }
}