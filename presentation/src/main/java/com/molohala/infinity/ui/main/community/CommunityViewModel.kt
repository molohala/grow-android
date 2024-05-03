package com.molohala.infinity.ui.main.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.constant.Constant
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.community.response.CommunityResponse
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.global.dto.request.PageRequest
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommunityState(
    val page: Int = 1,
    val communities: FetchFlow<List<CommunityResponse>> = FetchFlow.Fetching(),
    val isRefresh: Boolean = false
)

class CommunityViewModel : ViewModel() {

    val uiState = MutableStateFlow(CommunityState())

    fun fetchCommunities() {
        val nextPage = 1
        viewModelScope.launch {
            try {
                uiState.update { it.copy(communities = FetchFlow.Fetching()) }
                val communities = RetrofitClient.communityApi.getCommunities(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                if (communities.isNotEmpty()) {
                    uiState.update {
                        it.copy(
                            communities = FetchFlow.Success(communities)
                        )
                    }
                }
            } catch (e: Exception) {
                uiState.update {
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
        val communities = uiState.value.communities as? FetchFlow.Success?: run {
            uiState.update { it.copy(communities = FetchFlow.Failure()) }
            return
        }
        viewModelScope.launch {
            try {
                val nextPage = communities.data.size / Constant.pageInterval + 1
                val communities = RetrofitClient.communityApi.getCommunities(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                val oldCommunities = communities.toMutableList()
                oldCommunities.addAll(communities)
                uiState.update {
                    it.copy(
                        communities = FetchFlow.Success(oldCommunities)
                    )
                }
            } catch (e: Exception) {
                uiState.update {
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
        uiState.update { it.copy(isRefresh = false) }
    }
}