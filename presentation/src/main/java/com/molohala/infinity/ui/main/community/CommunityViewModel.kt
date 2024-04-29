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
    val communities: List<CommunityResponse> = arrayListOf(),
    val page: Int = 1,
    val communityFetchFlow: FetchFlow = FetchFlow.Fetching
)

class CommunityViewModel : ViewModel() {

    val uiState = MutableStateFlow(CommunityState())

    fun fetchCommunities() {
        uiState.update { it.copy(communityFetchFlow = FetchFlow.Fetching) }
        viewModelScope.launch {
            try {
                val nextPage = 1
                val communities = RetrofitClient.communityApi.getCommunities(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                if (communities.isNotEmpty()) {
                    uiState.update {
                        it.copy(
                            communities = communities,
                            communityFetchFlow = FetchFlow.Success
                        )
                    }
                }
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        communities = arrayListOf(),
                        page = 1,
                        communityFetchFlow = FetchFlow.Failure
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    fun fetchNextCommunities() {
        viewModelScope.launch {
            try {
                val nextPage = uiState.value.communities.size / Constant.pageInterval + 1
                val communities = RetrofitClient.communityApi.getCommunities(
                    page = nextPage,
                    size = Constant.pageInterval
                ).data
                val oldCommunities = uiState.value.communities.toMutableList()
                oldCommunities.addAll(communities)
                uiState.update {
                    it.copy(
                        communities = oldCommunities,
                        communityFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        communities = arrayListOf(),
                        page = 1,
                        communityFetchFlow = FetchFlow.Failure
                    )
                }
                Log.d(TAG, "fetchCommunities: $e")
            }
        }
    }

    /**
     *
     *     @MainActor
     *     public func fetchNextCommunities() {
     *         Task {
     *             do {
     *                 let nextPage = communities.count / pagingInterval + 1
     *                 print("\(#function) - fetching ... nextPage: \(nextPage)")
     *                 let request = PageRequest(page: nextPage, size: pagingInterval)
     *
     *                 let pagedCommunities = try await getCommunitesUseCase(request)
     *                 communities.append(contentsOf: pagedCommunities)
     *             } catch {
     *                 communities = []
     *                 page = 1
     *                 print("❌ 커뮤니티 페이징 실패")
     *             }
     *         }
     *     }
     *
     */
}