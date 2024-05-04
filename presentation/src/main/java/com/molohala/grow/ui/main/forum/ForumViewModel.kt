package com.molohala.grow.ui.main.forum

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.grow.common.constant.Constant
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.forum.response.ForumResponse
import com.molohala.grow.data.global.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ForumState(
    val page: Int = 1,
    val communities: FetchFlow<List<ForumResponse>> = FetchFlow.Fetching(),
    val isRefresh: Boolean = false
)

class ForumViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ForumState())
    val uiState = _uiState.asStateFlow()

    fun fetchCommunities() {
        val nextPage = 1
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(communities = FetchFlow.Fetching()) }
                val communities = RetrofitClient.forumApi.getForums(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                if (communities.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            communities = FetchFlow.Success(communities)
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        page = 1,
                        communities = FetchFlow.Failure()
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    fun fetchNextCommunities() {
        val communities = _uiState.value.communities as? FetchFlow.Success?: run {
            _uiState.update { it.copy(communities = FetchFlow.Failure()) }
            return
        }
        viewModelScope.launch {
            try {
                val nextPage = communities.data.size / Constant.pageInterval + 1
                val communities = RetrofitClient.forumApi.getForums(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                val oldCommunities = communities.toMutableList()
                oldCommunities.addAll(communities)
                _uiState.update {
                    it.copy(
                        communities = FetchFlow.Success(oldCommunities)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        communities = FetchFlow.Failure(),
                        page = 1
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    fun refresh() {
        fetchCommunities()
        _uiState.update { it.copy(isRefresh = false) }
    }
}