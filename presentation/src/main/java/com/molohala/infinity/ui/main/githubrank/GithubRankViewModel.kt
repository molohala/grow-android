package com.molohala.infinity.ui.main.githubrank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.rank.response.RankResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class GithubRankTab(
    val label: String
) {
    WEEK(label = "이번 주"),
    TOTAL(label = "전체")
}

data class GithubRankState(
    val githubRanks: List<RankResponse> = arrayListOf(),
    val githubRanksFetchFlow: FetchFlow = FetchFlow.Fetching,
    val selectedTab: GithubRankTab = GithubRankTab.WEEK,
    val isRefresh: Boolean = false
)

class GithubRankViewModel : ViewModel() {

    val uiState = MutableStateFlow(GithubRankState())

    fun fetchGithubRank() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(githubRanksFetchFlow = FetchFlow.Fetching) }
                val response = when (uiState.value.selectedTab) {
                    GithubRankTab.WEEK -> RetrofitClient.rankApi.getWeekGithubRank()
                    GithubRankTab.TOTAL -> RetrofitClient.rankApi.getTotalGithubRank()
                }.data
                uiState.update {
                    it.copy(
                        githubRanks = response,
                        githubRanksFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(githubRanksFetchFlow = FetchFlow.Failure) }
                Log.d(TAG, "fetchGithubRank: ${e.localizedMessage}")
            }
        }
    }

    fun clickTab(selectedTab: GithubRankTab) {
        uiState.update { it.copy(selectedTab = selectedTab) }
        fetchGithubRank()
    }

    fun refresh() {
        uiState.update { it.copy(isRefresh = false) }
        fetchGithubRank()
    }
}