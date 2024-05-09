package com.molohala.grow.ui.main.profiledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.grow.common.chart.baekjoonWeekChartInfo
import com.molohala.grow.common.chart.githubWeekChartInfo
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.info.response.GithubResponse
import com.molohala.grow.data.info.response.ProfileResponse
import com.molohala.grow.data.info.response.SolvedacResponse
import com.molohala.grow.data.language.response.LanguageResponse
import com.molohala.grow.designsystem.specific.chart.GrowChartInfo
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileDetailState(
    val isRefresh: Boolean = false,
    val profile: FetchFlow<ProfileResponse> = FetchFlow.Fetching(),
    val github: FetchFlow<GithubResponse?> = FetchFlow.Fetching(),
    val baekjoon: FetchFlow<SolvedacResponse?> = FetchFlow.Fetching(),
    val myLanguage: FetchFlow<List<LanguageResponse>> = FetchFlow.Fetching(),
    val githubChartInfo: FetchFlow<GrowChartInfo> = FetchFlow.Fetching(),
    val baekjoonChartInfo: FetchFlow<GrowChartInfo> = FetchFlow.Fetching(),
)

class ProfileDetailViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileDetailState())
    val uiState = _uiState.asStateFlow()

    fun refresh() {
        _uiState.update { it.copy(isRefresh = false) }
    }

    fun fetchProfile(memberId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(profile = FetchFlow.Fetching()) }
                val profile = RetrofitClient.infoApi.getProfileById(id = memberId).data ?: return@launch
                _uiState.update { it.copy(profile = FetchFlow.Success(profile)) }
                fetchGithub()
                fetchBaekjoon()
                fetchMyLanguage()
            } catch (e: Exception) {
                _uiState.update { it.copy(profile = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchGithub() {
        val profile = uiState.value.profile as? FetchFlow.Success ?: run {
            _uiState.update { it.copy(github = FetchFlow.Failure()) }
            return
        }
        val github = profile.data.socialAccounts.firstOrNull { it.socialType == "GITHUB" } ?: run {
            _uiState.update { it.copy(github = FetchFlow.Success(null)) }
            return
        }
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(github = FetchFlow.Fetching()) }
                val githubResponse =
                    RetrofitClient.infoApi.getGithubInfo(name = github.socialId).data
                        ?: return@launch
                _uiState.update {
                    it.copy(
                        github = FetchFlow.Success(githubResponse),
                        githubChartInfo = FetchFlow.Success(githubResponse.weekCommits.githubWeekChartInfo)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(github = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchBaekjoon() {
        val profile = uiState.value.profile as? FetchFlow.Success ?: run {
            _uiState.update { it.copy(baekjoon = FetchFlow.Failure()) }
            return
        }
        val solvedac =
            profile.data.socialAccounts.firstOrNull { it.socialType == "SOLVED_AC" } ?: run {
                _uiState.update { it.copy(baekjoon = FetchFlow.Success(null)) }
                return
            }
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(baekjoon = FetchFlow.Fetching()) }
                val solvedacResponse =
                    RetrofitClient.infoApi.getSolvedacInfo(name = solvedac.socialId).data
                        ?: return@launch
                _uiState.update {
                    it.copy(
                        baekjoon = FetchFlow.Success(solvedacResponse),
                        baekjoonChartInfo = FetchFlow.Success(solvedacResponse.weekSolves.baekjoonWeekChartInfo)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(baekjoon = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchMyLanguage() {
        launch {
            try {
                _uiState.update { it.copy(myLanguage = FetchFlow.Fetching()) }
                val myLanguage = RetrofitClient.languageApi.getMyLanguage().data?: return@launch
                _uiState.update { it.copy(myLanguage = FetchFlow.Success(myLanguage)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(myLanguage = FetchFlow.Failure()) }
            }
        }
    }
}