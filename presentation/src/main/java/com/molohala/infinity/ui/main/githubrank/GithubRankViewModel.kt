package com.molohala.infinity.ui.main.githubrank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.rank.response.RankResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class GithubRankTab(
    val label: String
) {
    WEEK(label = "이번 주"),
    TOTAL(label = "전체")
}

data class GithubRankState(
    val githubRanksFetchFlow: FetchFlow<List<RankResponse>> = FetchFlow.Fetching(),
    val selectedTab: GithubRankTab = GithubRankTab.WEEK,
    val isRefresh: Boolean = false
)

class GithubRankViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GithubRankState())
    val uiState = _uiState.asStateFlow()

    fun fetchGithubRank() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(githubRanksFetchFlow = FetchFlow.Fetching()) }
                val response = when (_uiState.value.selectedTab) {
                    GithubRankTab.WEEK -> RetrofitClient.rankApi.getWeekGithubRank()
                    GithubRankTab.TOTAL -> RetrofitClient.rankApi.getTotalGithubRank()
                }.data
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

    fun clickTab(selectedTab: GithubRankTab) {
        _uiState.update { it.copy(selectedTab = selectedTab) }
        fetchGithubRank()
    }

    fun refresh() {
        _uiState.update { it.copy(isRefresh = false) }
        fetchGithubRank()
    }
}