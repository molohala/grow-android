package com.molohala.infinity.ui.root

import androidx.lifecycle.ViewModel
import com.molohala.infinity.application.InfinityApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {

    val accessToken = MutableStateFlow(InfinityApp.prefs.accessToken)
    val refreshToken = MutableStateFlow(InfinityApp.prefs.refreshToken)

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
}