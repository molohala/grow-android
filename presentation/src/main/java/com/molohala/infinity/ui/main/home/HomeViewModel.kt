package com.molohala.infinity.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.community.response.CommunityResponse
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.rank.response.RankResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val weekCommunities: List<CommunityResponse> = arrayListOf(),
    val weekCommunitiesFetchFlow: FetchFlow = FetchFlow.Fetching,
    val todayGithubRanks: List<RankResponse> = arrayListOf(),
    val todayGithubRanksFetchFlow: FetchFlow = FetchFlow.Fetching,
    val todayBaekjoonRanks: List<RankResponse> = arrayListOf(),
    val todayBaekjoonRanksFetchFlow: FetchFlow = FetchFlow.Fetching
)

class HomeViewModel : ViewModel() {

    val uiState = MutableStateFlow(HomeState())

    fun fetchTodayGithubRank() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(todayGithubRanksFetchFlow = FetchFlow.Fetching) }
                val ranks = RetrofitClient.rankApi.getTodayGithubRank().data
                uiState.update {
                    it.copy(
                        todayGithubRanks = ranks,
                        todayGithubRanksFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(todayGithubRanksFetchFlow = FetchFlow.Failure) }
            }
        }
    }

    fun fetchTodayBaekjoonRank() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(todayBaekjoonRanksFetchFlow = FetchFlow.Fetching) }
                val ranks = RetrofitClient.rankApi.getTodaySolvedacRank().data
                uiState.update {
                    it.copy(
                        todayBaekjoonRanks = ranks,
                        todayBaekjoonRanksFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(todayBaekjoonRanksFetchFlow = FetchFlow.Failure) }
            }
        }
    }

    fun fetchWeekCommunities() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(weekCommunitiesFetchFlow = FetchFlow.Fetching) }
                val communities = RetrofitClient.communityApi.getBestCommunities().data
                uiState.update {
                    it.copy(
                        weekCommunities = communities,
                        weekCommunitiesFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(weekCommunitiesFetchFlow = FetchFlow.Failure) }
            }
        }
    }
}