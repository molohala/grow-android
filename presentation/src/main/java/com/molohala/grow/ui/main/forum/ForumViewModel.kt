package com.molohala.grow.ui.main.forum

import android.util.Log
import androidx.lifecycle.ViewModel
import com.molohala.grow.common.constant.Constant
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.forum.response.ForumResponse
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ForumState(
    val page: Int = 1,
    val forums: FetchFlow<List<ForumResponse>> = FetchFlow.Fetching(),
    val editForumFlow: FetchFlow<Boolean> = FetchFlow.Fetching(),
    val isRefresh: Boolean = false
)

sealed interface ForumSideEffect {
    data object RemoveForumSuccess: ForumSideEffect
    data object RemoveForumFailure: ForumSideEffect
}

class ForumViewModel : ViewModel() {

    private val _uiEffect = MutableSharedFlow<ForumSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(ForumState())
    val uiState = _uiState.asStateFlow()

    fun fetchCommunities() {
        val nextPage = 1
        launch {
            try {
                _uiState.update { it.copy(forums = FetchFlow.Fetching()) }
                val communities = RetrofitClient.forumApi.getForums(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data?: return@launch
                if (communities.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            forums = FetchFlow.Success(communities)
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        page = 1,
                        forums = FetchFlow.Failure()
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    fun fetchNextCommunities() {
        val forums = _uiState.value.forums as? FetchFlow.Success?: run {
            _uiState.update { it.copy(forums = FetchFlow.Failure()) }
            return
        }
        launch {
            try {
                val nextPage = forums.data.size / Constant.pageInterval + 1
                val newForums = RetrofitClient.forumApi.getForums(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data?: return@launch
                val oldForums = forums.data.toMutableList()
                oldForums.addAll(newForums)
                _uiState.update {
                    it.copy(
                        forums = FetchFlow.Success(oldForums)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        forums = FetchFlow.Failure(),
                        page = 1
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    fun patchLike(forumId: Int) {
        val forums = ((_uiState.value.forums as? FetchFlow.Success)?.data?: return).toMutableList()
        launch {
            try {
                RetrofitClient.likeApi.patchLike(forumId)
                forums.forEachIndexed { idx, i ->
                    if (i.forum.forumId == forumId) {
                        val forum = forums[idx]
                        val added = if (forum.forum.liked) -1 else 1
                        forums[idx] = forum.copy(forum = forum.forum.copy(like = forum.forum.like + added, liked = !forum.forum.liked))
                    }
                }
                _uiState.update { it.copy(forums = FetchFlow.Success(forums)) }
            } catch (e: Exception) {
                Log.e(TAG, "patchLike: $e")
            }
        }
    }

    fun removeForum(forumId: Int) {
        launch {
            try {
                RetrofitClient.forumApi.removeForum(forumId)
                fetchCommunities()
                _uiEffect.emit(ForumSideEffect.RemoveForumSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(ForumSideEffect.RemoveForumFailure)
            }
        }
    }

    fun refresh() {
        fetchCommunities()
        _uiState.update { it.copy(isRefresh = false) }
    }
}