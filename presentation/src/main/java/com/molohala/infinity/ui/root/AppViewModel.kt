package com.molohala.infinity.ui.root

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.info.response.GithubResponse
import com.molohala.infinity.data.info.response.ProfileResponse
import com.molohala.infinity.data.info.response.SolvedacResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AppSideEffect {
    data object Success: AppSideEffect
}

data class AppState(
    val profile: ProfileResponse? = null,
    val github: GithubResponse? = null,
    val solvedac: SolvedacResponse? = null
)

class AppViewModel: ViewModel() {

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
                val profile = RetrofitClient.infoApi.getProfile().data
                uiState.update { it.copy(profile = profile) }
                uiEffect.emit(AppSideEffect.Success)
            } catch (e: Exception) {
                Log.d(TAG, "fetchProfile: $e")
                clearToken()
            }
        }
    }

    fun fetchGithub() {
        val profile = uiState.value.profile?: return
        val github = profile.socialAccounts.firstOrNull { it.socialType == "GITHUB" }?: return
        viewModelScope.launch {
            try {
                val github = RetrofitClient.infoApi.getGithubInfo(name = github.socialId).data
                uiState.update { it.copy(github = github) }
            } catch (e: Exception) {
                Log.d(TAG, "fetchGithub: $e")
            }
        }
    }

    fun fetchSolvedac() {
        val profile = uiState.value.profile?: return
        val solvedac = profile.socialAccounts.firstOrNull { it.socialType == "SOLVED_AC" }?: return
        viewModelScope.launch {
            try {
                val solvedac = RetrofitClient.infoApi.getSolvedacInfo(name = solvedac.socialId).data
                uiState.update { it.copy(solvedac = solvedac) }
            } catch (e: Exception) {
                Log.d(TAG, "fetchGithub: $e")
            }
        }
    }
}