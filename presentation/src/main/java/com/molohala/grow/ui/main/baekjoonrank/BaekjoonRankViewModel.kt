package com.molohala.grow.ui.main.baekjoonrank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.rank.response.RankResponse
import com.molohala.grow.data.rank.response.UpdateRankResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class BaekjoonRankTab(
    val label: String
) {
    WEEK(label = "이번 주"),
    TOTAL(label = "전체")
}

data class BaekjoonRankState(
    val githubRanksFetchFlow: FetchFlow<UpdateRankResponse> = FetchFlow.Fetching(),
    val selectedTab: BaekjoonRankTab = BaekjoonRankTab.WEEK,
    val isRefresh: Boolean = false
)

class BaekjoonRankViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BaekjoonRankState())
    val uiState = _uiState.asStateFlow()

    fun fetchBaekjoonRank() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(githubRanksFetchFlow = FetchFlow.Fetching()) }
                val response = when (_uiState.value.selectedTab) {
                    BaekjoonRankTab.WEEK -> RetrofitClient.rankApi.getWeekSolvedacRank()
                    BaekjoonRankTab.TOTAL -> RetrofitClient.rankApi.getTotalSolvedacRank()
                }.data?: return@launch
                _uiState.update {
                    it.copy(
                        githubRanksFetchFlow = FetchFlow.Success(response)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(githubRanksFetchFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun clickTab(selectedTab: BaekjoonRankTab) {
        _uiState.update { it.copy(selectedTab = selectedTab) }
        fetchBaekjoonRank()
    }

    fun refresh() {
        _uiState.update { it.copy(isRefresh = false) }
        fetchBaekjoonRank()
    }
}