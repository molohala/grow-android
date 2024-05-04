package com.molohala.grow.ui.main.forumdetail

import androidx.lifecycle.ViewModel
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.comment.request.CreateCommentRequest
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.data.forum.response.ForumContentResponse
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ForumDetailState(
    val forum: FetchFlow<ForumContentResponse> = FetchFlow.Fetching(),
    val comments: FetchFlow<List<CommentResponse>> = FetchFlow.Fetching(),
    val createCommentFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val removeForumFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val removeCommentFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val selectedRemoveComment: CommentResponse? = null,
    val currentComment: String = ""
)

class ForumDetailViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ForumDetailState())
    val uiState = _uiState.asStateFlow()

    fun fetchForum(forumId: Int) {
        launch {
            try {
                _uiState.update { it.copy(forum = FetchFlow.Fetching()) }
                val forum = RetrofitClient.forumApi.getForum(forumId).data
                _uiState.update { it.copy(forum = FetchFlow.Success(forum)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(forum = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchComments(forumId: Int) {
        launch {
            try {
                _uiState.update { it.copy(comments = FetchFlow.Fetching()) }
                val comments = RetrofitClient.commentApi.getComment(forumId).data
                _uiState.update { it.copy(comments = FetchFlow.Success(comments)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(comments = FetchFlow.Failure()) }
            }
        }
    }

    fun createComment(forumId: Int) {
        launch {
            try {
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Fetching()) }
                val request = CreateCommentRequest(content = _uiState.value.currentComment, forum = forumId)
                RetrofitClient.commentApi.createComment(request)
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Success(true)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun patchLike(forumId: Int) {
        launch {
            try {
                RetrofitClient.likeApi.patchLike(forumId)
                val forum = (_uiState.value.forum as? FetchFlow.Success)?.data?: return@launch
                val added = if (forum.liked) -1 else 1
                val newForum = forum.copy(like = forum.like + added)
                _uiState.update { it.copy(forum = FetchFlow.Success(newForum)) }
            } catch (e: Exception) {}
        }
    }

    fun removeForum(forumId: Int) {
        launch {
            try {
                RetrofitClient.forumApi.removeForum(forumId)
                _uiState.update { it.copy(removeForumFlow = FetchFlow.Success(true)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(removeForumFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun removeComment(forumId: Int) {
        launch {
            try {
                _uiState.update { it.copy(removeCommentFlow = FetchFlow.Fetching()) }
                RetrofitClient.commentApi.removeComment(forumId)
                fetchComments(forumId)
                _uiState.update { it.copy(removeCommentFlow = FetchFlow.Success(true)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(removeCommentFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun onClickRemoveComment(comment: CommentResponse) {
        _uiState.update { it.copy(selectedRemoveComment = comment) }
    }
}