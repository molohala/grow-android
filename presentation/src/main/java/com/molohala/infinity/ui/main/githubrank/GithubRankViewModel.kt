package com.molohala.infinity.ui.main.githubrank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.rank.response.GithubRankResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GithubRankState(
    val githubRanks: List<GithubRankResponse> = arrayListOf()
)

class GithubRankViewModel: ViewModel() {

    val githubRanks = MutableStateFlow(GithubRankState())

    fun fetchGithubRank() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.rankApi.getWeekGithubRank().data
                githubRanks.update { it.copy(githubRanks = response) }
            } catch (e: Exception) {
                Log.d(TAG, "fetchGithubRank: ${e.localizedMessage}")
            }
        }
    }
}