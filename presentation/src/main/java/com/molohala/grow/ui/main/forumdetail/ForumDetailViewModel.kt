package com.molohala.grow.ui.main.forumdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.comment.request.CreateCommentRequest
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.data.forum.response.ForumContentResponse
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.opengraph.OpenGraph
import com.molohala.grow.data.report.request.ReportRequest
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ForumDetailState(
    val forum: FetchFlow<ForumContentResponse> = FetchFlow.Fetching(),
    val comments: FetchFlow<List<CommentResponse>> = FetchFlow.Fetching(),
    val currentComment: String = "",
    val reportReason: String = "",
    val selectedReportComment: CommentResponse? = null,
    val isRefresh: Boolean = false,
)

sealed interface ForumDetailSideEffect {
    data object RemoveForumSuccess : ForumDetailSideEffect
    data object RemoveForumFailure : ForumDetailSideEffect
    data object RemoveCommentSuccess : ForumDetailSideEffect
    data object RemoveCommentFailure : ForumDetailSideEffect
    data object CreateCommentSuccess : ForumDetailSideEffect
    data object CreateCommentFailure : ForumDetailSideEffect
    data object ReportSuccess : ForumDetailSideEffect
}

class ForumDetailViewModel : ViewModel() {

    private val _uiEffect = MutableSharedFlow<ForumDetailSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(ForumDetailState())
    val uiState = _uiState.asStateFlow()

    fun fetchForum(forumId: Int) {
        launch {
            try {
                _uiState.update { it.copy(forum = FetchFlow.Fetching()) }
                val forum = RetrofitClient.forumApi.getForum(forumId).data ?: return@launch
                _uiState.update { it.copy(forum = FetchFlow.Success(forum)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(forum = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchComments(forumId: Int) {
        launch {
            try {
                val comments = RetrofitClient.commentApi.getComment(forumId).data ?: return@launch
                _uiState.update { it.copy(comments = FetchFlow.Success(comments)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(comments = FetchFlow.Failure()) }
            }
        }
    }

    fun createComment(forumId: Int) {
        launch {
            try {
                val request =
                    CreateCommentRequest(content = _uiState.value.currentComment, forum = forumId)
                RetrofitClient.commentApi.createComment(request)
                fetchComments(forumId)
                _uiState.update {
                    it.copy(
                        currentComment = ""
                    )
                }
                _uiEffect.emit(ForumDetailSideEffect.CreateCommentSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(ForumDetailSideEffect.CreateCommentFailure)
            }
        }
    }

    fun patchLike(forumId: Int) {
        launch {
            try {
                RetrofitClient.likeApi.patchLike(forumId)
                val forum = (_uiState.value.forum as? FetchFlow.Success)?.data ?: return@launch
                val added = if (forum.liked) -1 else 1
                val newForum = forum.copy(like = forum.like + added, liked = !forum.liked)
                _uiState.update { it.copy(forum = FetchFlow.Success(newForum)) }
            } catch (e: Exception) {
                Log.d(TAG, "patchLike: ${e.printStackTrace()}")
            }
        }
    }

    fun removeForum(forumId: Int) {
        launch {
            try {
                RetrofitClient.forumApi.removeForum(forumId)
                _uiEffect.emit(ForumDetailSideEffect.RemoveForumSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(ForumDetailSideEffect.RemoveForumFailure)
            }
        }
    }

    fun removeComment(forumId: Int, commentId: Int) {
        launch {
            try {
                RetrofitClient.commentApi.removeComment(commentId)
                fetchComments(forumId)
                _uiEffect.emit(ForumDetailSideEffect.RemoveCommentSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(ForumDetailSideEffect.RemoveCommentFailure)
            }
        }
    }

    fun reportComment() {

        val comment = _uiState.value.selectedReportComment ?: return
        val reason = _uiState.value.reportReason

        launch {
            try {
                val request = ReportRequest(
                    reason = reason
                )
                RetrofitClient.commentApi.reportComment(
                    id = comment.commentId,
                    req = request
                )
                _uiState.update { it.copy(reportReason = "") }
                _uiEffect.emit(ForumDetailSideEffect.ReportSuccess)
            } catch (e: Exception) {
            }
        }
    }


    fun reportForum() {

        val forum = _uiState.value.forum as? FetchFlow.Success ?: return
        val reason = _uiState.value.reportReason

        launch {
            try {
                val request = ReportRequest(
                    reason = reason
                )
                RetrofitClient.forumApi.reportCommunity(
                    id = forum.data.forumId,
                    req = request
                )
                _uiState.update { it.copy(reportReason = "") }
                _uiEffect.emit(ForumDetailSideEffect.ReportSuccess)
            } catch (_: Exception) {
            }
        }
    }

    fun updateCurrentComment(comment: String) {
        _uiState.update { it.copy(currentComment = comment) }
    }

    fun updateReportReason(reason: String) {
        _uiState.update { it.copy(reportReason = reason) }
    }

    fun updateReportComment(comment: CommentResponse) {
        _uiState.update { it.copy(selectedReportComment = comment) }
    }

    fun refresh(forumId: Int) {
        _uiState.update { it.copy(isRefresh = false) }
        fetchForum(forumId)
    }

    fun updateOpenGraph(openGraph: OpenGraph) {
        val forum = _uiState.value.forum as? FetchFlow.Success ?: return
        _uiState.update {
            it.copy(
                forum = FetchFlow.Success(forum.data.copy(openGraph = openGraph))
            )
        }
    }
}