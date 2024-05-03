package com.molohala.grow.ui.main.communitydetail

import androidx.lifecycle.ViewModel
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.comment.request.CreateCommentRequest
import com.molohala.grow.data.comment.response.CommentResponse
import com.molohala.grow.data.community.response.CommunityContentResponse
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CommunityDetailState(
    val community: FetchFlow<CommunityContentResponse> = FetchFlow.Fetching(),
    val comments: FetchFlow<List<CommentResponse>> = FetchFlow.Fetching(),
    val createCommentFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val removeCommunityFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val removeCommentFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val selectedRemoveComment: CommentResponse? = null,
    val currentComment: String = ""
)

class CommunityDetailViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CommunityDetailState())
    val uiState = _uiState.asStateFlow()

    fun fetchCommunity(communityId: Int) {
        launch {
            try {
                _uiState.update { it.copy(community = FetchFlow.Fetching()) }
                val community = RetrofitClient.communityApi.getCommunity(communityId).data
                _uiState.update { it.copy(community = FetchFlow.Success(community)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(community = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchComments(communityId: Int) {
        launch {
            try {
                _uiState.update { it.copy(comments = FetchFlow.Fetching()) }
                val comments = RetrofitClient.commentApi.getComment(communityId).data
                _uiState.update { it.copy(comments = FetchFlow.Success(comments)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(comments = FetchFlow.Failure()) }
            }
        }
    }

    fun createComment(communityId: Int) {
        launch {
            try {
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Fetching()) }
                val request = CreateCommentRequest(content = _uiState.value.currentComment, communityId = communityId)
                RetrofitClient.commentApi.createComment(request)
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Success(true)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(createCommentFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun patchLike(communityId: Int) {
        launch {
            try {
                RetrofitClient.likeApi.patchLike(communityId)
                val community = (_uiState.value.community as? FetchFlow.Success)?.data?: return@launch
                val added = if (community.liked) -1 else 1
                val newCommunity = community.copy(like = community.like + added)
                _uiState.update { it.copy(community = FetchFlow.Success(newCommunity)) }
            } catch (e: Exception) {}
        }
    }

    fun removeCommunity(communityId: Int) {
        launch {
            try {
                RetrofitClient.communityApi.removeCommunity(communityId)
                _uiState.update { it.copy(removeCommunityFlow = FetchFlow.Success(true)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(removeCommunityFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun removeComment(communityId: Int) {
        launch {
            try {
                _uiState.update { it.copy(removeCommentFlow = FetchFlow.Fetching()) }
                RetrofitClient.commentApi.removeComment(communityId)
                fetchComments(communityId)
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