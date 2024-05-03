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
    val profile: FetchFlow<ProfileResponse> = FetchFlow.Fetching(),
    val githubFetchFlow: FetchFlow<GithubResponse?> = FetchFlow.Fetching(),
    val solvedac: FetchFlow<SolvedacResponse?> = FetchFlow.Fetching(),
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
                uiState.update { it.copy(profile = FetchFlow.Fetching()) }
                val profile = RetrofitClient.infoApi.getProfile().data
                uiState.update { it.copy(profile = FetchFlow.Success(profile)) }
                uiEffect.emit(AppSideEffect.Success)
                fetchGithub()
                fetchSolvedac()
            } catch (e: Exception) {
                Log.d(TAG, "fetchProfile: $e")
                uiState.update { it.copy(profile = FetchFlow.Failure()) }
                clearToken()
            }
        }
    }

    fun fetchGithub() {
        val profile = uiState.value.profile as? FetchFlow.Success ?: run {
            uiState.update { it.copy(githubFetchFlow = FetchFlow.Failure()) }
            return
        }
        val github = profile.data.socialAccounts.firstOrNull { it.socialType == "GITHUB" } ?: run {
            uiState.update { it.copy(githubFetchFlow = FetchFlow.Success(null)) }
            return
        }
        viewModelScope.launch {
            try {
                uiState.update { it.copy(githubFetchFlow = FetchFlow.Fetching()) }
                val githubResponse =
                    RetrofitClient.infoApi.getGithubInfo(name = github.socialId).data
                uiState.update {
                    it.copy(
                        githubFetchFlow = FetchFlow.Success(githubResponse)
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(githubFetchFlow = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchSolvedac() {
        val profile = uiState.value.profile as? FetchFlow.Success ?: run {
            uiState.update { it.copy(solvedac = FetchFlow.Failure()) }
            return
        }
        val solvedac =
            profile.data.socialAccounts.firstOrNull { it.socialType == "SOLVED_AC" } ?: run {
                uiState.update { it.copy(solvedac = FetchFlow.Success(null)) }
                return
            }
        viewModelScope.launch {
            try {
                uiState.update { it.copy(solvedac = FetchFlow.Fetching()) }
                val solvedacResponse =
                    RetrofitClient.infoApi.getSolvedacInfo(name = solvedac.socialId).data
                uiState.update {
                    it.copy(
                        solvedac = FetchFlow.Success(solvedacResponse)
                    )
                }
            } catch (e: Exception) {
                uiState.update { it.copy(solvedac = FetchFlow.Failure()) }
            }
        }
    }

    fun clickTab(tab: BottomNavigationType) {
        uiState.update { it.copy(selectedTab = tab) }
    }
}