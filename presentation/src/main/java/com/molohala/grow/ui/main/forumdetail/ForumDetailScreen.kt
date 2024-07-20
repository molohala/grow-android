package com.molohala.grow.ui.main.forumdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bestswlkh0310.mydesignsystem.component.avatar.AvatarType
import com.bestswlkh0310.mydesignsystem.component.avatar.MyAvatar
import com.bestswlkh0310.mydesignsystem.component.button.MyLikeButton
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.component.divider.MyDivider
import com.bestswlkh0310.mydesignsystem.component.menu.MenuType
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenu
import com.bestswlkh0310.mydesignsystem.component.menu.MyMenuData
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.extension.`if`
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.iconography.MyIcon
import com.bestswlkh0310.mydesignsystem.foundation.util.timeAgo
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.data.forum.response.ForumContentResponse
import com.molohala.grow.specific.comment.GrowCommentCell
import com.molohala.grow.specific.comment.GrowCommentCellShimmer
import com.molohala.grow.specific.text.LinkifyText
import com.molohala.grow.ui.main.main.NavGroup
import com.molohala.grow.ui.root.AppState
import com.molohala.grow.ui.root.AppViewModel
import com.bestswlkh0310.mydesignsystem.R as DR

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumDetailScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    viewModel: ForumDetailViewModel = viewModel(),
    forumId: Int
) {

    val context = LocalContext.current
    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefresh, onRefresh = {
            viewModel.refresh(forumId)
        }
    )
    var showRemoveForumDialog by remember { mutableStateOf(false) }
    var showRemoveForumSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveForumFailureDialog by remember { mutableStateOf(false) }
    var showRemoveCommentDialog by remember { mutableStateOf(false) }
    var showRemoveCommentSuccessDialog by remember { mutableStateOf(false) }
    var showRemoveCommentFailureDialog by remember { mutableStateOf(false) }
    var showCommentReportDialog by remember { mutableStateOf(false) }
    var showForumReportDialog by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedCommentId by remember { mutableStateOf<Int?>(null) }
    val profileId = (uiAppState.profile as? FetchFlow.Success)?.data?.id ?: -1

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

                ForumDetailSideEffect.ReportSuccess -> {
                    Toast.makeText(context, "신고 접수 완료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MyTopAppBar(
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
            LazyColumn(
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    uiState.forum.let {
                        when (it) {
                            is FetchFlow.Failure -> {}

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
                                        },
                                        onReport = {
                                            showForumReportDialog = true
                                        },
                                        profileId = profileId
                                    )
                                    MyDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                    Comments(
                                        uiState = uiState,
                                        uiAppState = uiAppState,
                                        onReport = { comment ->
                                            viewModel.updateReportComment(comment)
                                            showCommentReportDialog = true
                                        },
                                        onRemove = {
                                            selectedCommentId = it
                                            showRemoveCommentDialog = true
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(64.dp))
                                }
                            }
                        }
                    }
                }
            }
            val isIconEnable = uiState.currentComment.isNotEmpty()
            val iconColor = if (isIconEnable) {
                MyTheme.colorScheme.textFieldPrimary
            } else {
                MyTheme.colorScheme.textFieldTextDisabled
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
                MyTextField(
                    modifier = Modifier
                        .clip(shape)
                        .background(MyTheme.colorScheme.background)
                        .weight(1f)
                        .heightIn(0.dp, 200.dp),
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
                    MyIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .`if`(isIconEnable) {
                                it.bounceClick(onClick = {
                                    viewModel.createComment(forumId)
                                })
                            },
                        id = DR.drawable.ic_send,
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
        MyDialog(
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
        MyDialog(
            title = "게시글 삭제 성공",
            onDismissRequest = {
                navController.popBackStack()
                showRemoveForumSuccessDialog = false
            },
        )
    }

    if (showRemoveForumFailureDialog) {
        MyDialog(
            title = "게시글 삭제 실패",
            onDismissRequest = {
                showRemoveForumFailureDialog = false
            }
        )
    }

    if (showRemoveCommentDialog) {
        MyDialog(
            title = "정말 댓글을 삭제하시겠습니까?",
            successText = "삭제하기",
            cancelText = "아니요",
            onCancelRequest = {
                showRemoveCommentDialog = false
            },
            onDismissRequest = {
                showRemoveCommentDialog = false
            },
            onSuccessRequest = {
                showRemoveCommentDialog = false
                val selectedCommentId = selectedCommentId ?: return@MyDialog
                viewModel.removeComment(forumId = forumId, commentId = selectedCommentId)
            }
        )
    }

    if (showRemoveCommentSuccessDialog) {
        MyDialog(
            title = "댓글 삭제 성공",
            onDismissRequest = {
                showRemoveCommentSuccessDialog = false
            },
        )
    }

    if (showRemoveCommentFailureDialog) {
        MyDialog(
            title = "댓글 삭제 실패",
            onDismissRequest = {
                showRemoveCommentFailureDialog = false
            },
        )
    }

    if (showCommentReportDialog) {
        MyDialog(
            title = "신고 내용을 적어 주세요",
            onSuccessRequest = {
                if (uiState.reportReason.isEmpty()) {
                    Toast.makeText(context, "신고 내용을 적어 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    showCommentReportDialog = false
                    viewModel.reportComment()
                }
            },
            onCancelRequest = {
                showCommentReportDialog = false
            },
            onDismissRequest = {
                showCommentReportDialog = false
            }
        ) {
            MyTextField(
                value = uiState.reportReason,
                onValueChange = viewModel::updateReportReason
            )
        }
    }

    if (showForumReportDialog) {
        MyDialog(
            title = "신고 내용을 적어 주세요",
            onSuccessRequest = {
                if (uiState.reportReason.isEmpty()) {
                    Toast.makeText(context, "신고 내용을 적어 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    showForumReportDialog = false
                    viewModel.reportForum()
                }
            },
            onCancelRequest = {
                showForumReportDialog = false
            },
            onDismissRequest = {
                showForumReportDialog = false
            }
        ) {
            MyTextField(
                value = uiState.reportReason,
                onValueChange = viewModel::updateReportReason
            )
        }
    }
}

@Composable
private fun Forum(
    profileId: Int,
    isMenuExpanded: Boolean,
    onChangeIsMenuExpanded: (Boolean) -> Unit,
    forum: ForumContentResponse,
    onClickLike: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    onReport: () -> Unit
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
            MyAvatar(type = AvatarType.Medium)
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = forum.writerName,
                    style = MyTheme.typography.bodyBold,
                    color = MyTheme.colorScheme.textNormal
                )
                Text(
                    text = forum.createdAt.timeAgo,
                    style = MaterialTheme.typography.labelMedium,
                    color = MyTheme.colorScheme.textAlt
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            val me = profileId == forum.writerId
            Column {
                MyIcon(
                    modifier = Modifier.bounceClick(onClick = {
                        onChangeIsMenuExpanded(true)
                    }),
                    id = DR.drawable.ic_detail_vertical,
                    color = MyTheme.colorScheme.textAlt
                )
                MyMenu(
                    expanded = isMenuExpanded,
                    menuList = if (me) listOf(
                        MyMenuData("수정하기", onClick = onEdit),
                        MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport),
                        MyMenuData("삭제하기", type = MenuType.Destructive, onClick = onRemove)
                    ) else listOf(
                        MyMenuData("신고하기", type = MenuType.Destructive, onClick = onReport)
                    ),
                    onDismissRequest = { onChangeIsMenuExpanded(false) }
                )
            }
        }
        SelectionContainer {
            LinkifyText(
                text = forum.content,
                style = MyTheme.typography.bodyRegular,
                color = MyTheme.colorScheme.textNormal
            )
        }
        MyLikeButton(
            like = forum.like,
            enabled = forum.liked,
            onClick = onClickLike
        )
    }
}

@Composable
private fun Comments(
    uiState: ForumDetailState,
    uiAppState: AppState,
    onReport: (CommentResponse) -> Unit,
    onRemove: (Int) -> Unit
) {
    uiState.comments.let {
        when (it) {
            is FetchFlow.Failure -> {}

            is FetchFlow.Fetching -> {
                GrowCommentCellShimmer()
            }

            is FetchFlow.Success -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    it.data.forEach { comment ->
                        val profile =
                            (uiAppState.profile as? FetchFlow.Success)?.data ?: return@forEach
                        GrowCommentCell(
                            comment = comment,
                            profileId = profile.id,
                            onReport = {
                                onReport(comment)
                            },
                            onRemove = {
                                onRemove(comment.commentId)
                            }
                        )
                    }
                }
            }
        }
    }
}