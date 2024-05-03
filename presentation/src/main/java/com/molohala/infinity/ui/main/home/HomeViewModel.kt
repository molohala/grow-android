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
    val weekCommunities: FetchFlow<List<CommunityResponse>> = FetchFlow.Fetching(),
    val todayGithubRanks: FetchFlow<List<RankResponse>> = FetchFlow.Fetching(),
    val todayBaekjoonRanks: FetchFlow<List<RankResponse>> = FetchFlow.Fetching(),
)

class HomeViewModel : ViewModel() {

    val uiState = MutableStateFlow(HomeState())

    fun fetchTodayGithubRank() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(todayGithubRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodayGithubRank().data
                uiState.update {
                    it.copy(
                        todayGithubRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(todayGithubRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchTodayBaekjoonRank() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodaySolvedacRank().data
                uiState.update {
                    it.copy(
                        todayBaekjoonRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchWeekCommunities() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(weekCommunities = FetchFlow.Fetching()) }
                val communities = RetrofitClient.communityApi.getBestCommunities().data
                uiState.update {
                    it.copy(
                        weekCommunities = FetchFlow.Success(communities)
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(weekCommunities = FetchFlow.Failure()) }
            }
        }
    }
}