package com.molohala.infinity.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.community.response.CommunityResponse
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.rank.response.RankResponse
import com.molohala.infinity.ui.util.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val weekCommunities: FetchFlow<List<CommunityResponse>> = FetchFlow.Fetching(),
    val todayGithubRanks: FetchFlow<List<RankResponse>> = FetchFlow.Fetching(),
    val todayBaekjoonRanks: FetchFlow<List<RankResponse>> = FetchFlow.Fetching(),
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun fetchTodayGithubRank() {
        launch {
            try {
                _uiState.update { it.copy(todayGithubRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodayGithubRank().data
                _uiState.update {
                    it.copy(
                        todayGithubRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(todayGithubRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchTodayBaekjoonRank() {
        launch {
            try {
                _uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodaySolvedacRank().data
                _uiState.update {
                    it.copy(
                        todayBaekjoonRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchWeekCommunities() {
        launch {
            try {
                _uiState.update { it.copy(weekCommunities = FetchFlow.Fetching()) }
                val communities = RetrofitClient.communityApi.getBestCommunities().data
                _uiState.update {
                    it.copy(
                        weekCommunities = FetchFlow.Success(communities)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(weekCommunities = FetchFlow.Failure()) }
            }
        }
    }
}