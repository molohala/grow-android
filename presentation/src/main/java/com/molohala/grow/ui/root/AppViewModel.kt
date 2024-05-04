package com.molohala.grow.ui.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.grow.application.InfinityApp
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.info.response.GithubResponse
import com.molohala.grow.data.info.response.ProfileResponse
import com.molohala.grow.data.info.response.SolvedacResponse
import com.molohala.grow.designsystem.component.bottomtabbar.BottomTabItemType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AppState(
    val accessToken: String = InfinityApp.prefs.accessToken,
    val refreshToken: String = InfinityApp.prefs.refreshToken,
    val profile: FetchFlow<ProfileResponse> = FetchFlow.Fetching(),
    val github: FetchFlow<GithubResponse?> = FetchFlow.Fetching(),
    val baekjoon: FetchFlow<SolvedacResponse?> = FetchFlow.Fetching(),
    val selectedTab: BottomTabItemType = BottomTabItemType.Home
)

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppState())
    val uiState = _uiState.asStateFlow()

    fun updateAccessToken(token: String) {
        InfinityApp.prefs.accessToken = token
        _uiState.update { it.copy(accessToken = token) }
    }

    fun updateRefreshToken(token: String) {
        InfinityApp.prefs.refreshToken = token
        _uiState.update { it.copy(refreshToken = token) }
    }

    fun clearToken() {
        InfinityApp.prefs.clearToken()
        with(InfinityApp.prefs) {
            updateAccessToken(accessToken)
            updateRefreshToken(refreshToken)
        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(profile = FetchFlow.Fetching()) }
                val profile = RetrofitClient.infoApi.getProfile().data
                _uiState.update { it.copy(profile = FetchFlow.Success(profile)) }
                fetchGithub()
                fetchSolvedac()
            } catch (e: Exception) {
                _uiState.update { it.copy(profile = FetchFlow.Failure()) }
                clearToken()
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
                _uiState.update {
                    it.copy(
                        github = FetchFlow.Success(githubResponse)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(github = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchSolvedac() {
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
                _uiState.update {
                    it.copy(
                        baekjoon = FetchFlow.Success(solvedacResponse)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(baekjoon = FetchFlow.Failure()) }
            }
        }
    }

    fun clickTab(tab: BottomTabItemType) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}