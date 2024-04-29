package com.molohala.infinity.ui.root

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.info.response.ProfileResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AppSideEffect {
}

class AppViewModel: ViewModel() {

    val accessToken = MutableStateFlow(InfinityApp.prefs.accessToken)
    val refreshToken = MutableStateFlow(InfinityApp.prefs.refreshToken)
    val uiEffect = MutableSharedFlow<AppSideEffect>()

    lateinit var profile: ProfileResponse

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
                profile = RetrofitClient.infoApi.getProfile().data
            } catch (e: Exception) {
                Log.d(TAG, "fetchProfile: $e")
                clearToken()
            }
        }
    }
}