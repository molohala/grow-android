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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
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
import com.molohala.grow.designsystem.component.divider.GrowDivider
import com.molohala.grow.designsystem.component.textfield.GrowTextField
import com.molohala.grow.designsystem.component.topappbar.GrowTopAppBar
import com.molohala.grow.designsystem.extension.bounceClick
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.iconography.GrowIcon
import com.molohala.grow.designsystem.specific.comment.GrowCommentCell
import com.molohala.grow.designsystem.specific.comment.GrowCommentCellShimmer
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
    LaunchedEffect(Unit) {
        viewModel.fetchForum(forumId)
        viewModel.fetchComments(forumId)
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
                                    Forum(forum = it.data)
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
                GrowIcon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Bottom)
                        .bounceClick(onClick = {
                            viewModel.createComment(forumId)
                        }),
                    id = R.drawable.ic_send,
                    color = iconColor
                )
            }

            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                refreshing = uiState.isRefresh,
                state = pullRefreshState
            )
        }
    }
}

@Composable
private fun Forum(forum: ForumContentResponse) {

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
            GrowIcon(
                modifier = Modifier.bounceClick(onClick = {

                }),
                id = R.drawable.ic_detail_vertical,
                color = GrowTheme.colorScheme.textAlt
            )
        }
        Text(
            text = forum.content,
            style = GrowTheme.typography.bodyRegular,
            overflow = TextOverflow.Ellipsis,
            maxLines = 5,
            color = GrowTheme.colorScheme.textNormal
        )
        GrowLikeButton(
            like = forum.like,
            enabled = forum.liked
        ) {

        }
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