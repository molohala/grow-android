package com.molohala.infinity.ui.root

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.info.response.GithubResponse
import com.molohala.infinity.data.info.response.ProfileResponse
import com.molohala.infinity.data.info.response.SolvedacResponse
import com.molohala.infinity.ui.main.main.BottomNavigationType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AppSideEffect {
    data object Success : AppSideEffect
}

data class AppState(
    val profile: ProfileResponse? = null,
    val github: GithubResponse? = null,
    val solvedac: SolvedacResponse? = null,
    val profileFetchFlow: FetchFlow = FetchFlow.Fetching,
    val githubFetchFlow: FetchFlow = FetchFlow.Fetching,
    val solvedacFetchFlow: FetchFlow = FetchFlow.Fetching,
    val selectedTab: BottomNavigationType = BottomNavigationType.Home
)

class AppViewModel : ViewModel() {

    val accessToken = MutableStateFlow(InfinityApp.prefs.accessToken)
    val refreshToken = MutableStateFlow(InfinityApp.prefs.refreshToken)
    val uiEffect = MutableSharedFlow<AppSideEffect>()
    val uiState = MutableStateFlow(AppState())

    fun updateAccessToken(token: String) {
        InfinityApp.prefs.accessToken = token
        accessToken.update { token }
    }

    fun updateRefreshToken(token: String) {
        InfinityApp.prefs.refreshToken = token
        refreshToken.update { token }
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
                uiState.update { it.copy(profileFetchFlow = FetchFlow.Fetching) }
                val profile = RetrofitClient.infoApi.getProfile().data
                uiState.update { it.copy(profile = profile) }
                uiEffect.emit(AppSideEffect.Success)
            } catch (e: Exception) {
                Log.d(TAG, "fetchProfile: $e")
                uiState.update { it.copy(profileFetchFlow = FetchFlow.Failure) }
                clearToken()
            }
        }
    }

    fun fetchGithub() {
        val profile = uiState.value.profile?: run {
            uiState.update { it.copy(githubFetchFlow = FetchFlow.Failure) }
            return
        }
        val github = profile.socialAccounts.firstOrNull { it.socialType == "GITHUB" }?: run {
            uiState.update { it.copy(githubFetchFlow = FetchFlow.Failure) }
            return
        }
        viewModelScope.launch {
            try {
                uiState.update { it.copy(githubFetchFlow = FetchFlow.Fetching) }
                val githubResponse = RetrofitClient.infoApi.getGithubInfo(name = github.socialId).data
                uiState.update {
                    it.copy(
                        github = githubResponse,
                        githubFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                Log.d(TAG, "fetchGithub: $e")
                uiState.update { it.copy(githubFetchFlow = FetchFlow.Failure) }
            }
        }
    }

    fun fetchSolvedac() {
        val profile = uiState.value.profile ?: run {
            uiState.update { it.copy(solvedacFetchFlow = FetchFlow.Failure) }
            return
        }
        val solvedac = profile.socialAccounts.firstOrNull { it.socialType == "SOLVED_AC" } ?: run {
            uiState.update { it.copy(solvedacFetchFlow = FetchFlow.Failure) }
            return
        }
        viewModelScope.launch {
            try {
                uiState.update { it.copy(solvedacFetchFlow = FetchFlow.Fetching) }
                val solvedac = RetrofitClient.infoApi.getSolvedacInfo(name = solvedac.socialId).data
                uiState.update {
                    it.copy(
                        solvedac = solvedac,
                        solvedacFetchFlow = FetchFlow.Success
                    )
                }
            } catch (e: Exception) {
                Log.d(TAG, "fetchGithub: $e")
                uiState.update { it.copy(solvedacFetchFlow = FetchFlow.Failure) }
            }
        }
    }

    fun clickTab(tab: BottomNavigationType) {
        uiState.update { it.copy(selectedTab = tab) }
    }
}