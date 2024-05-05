package com.molohala.grow.ui.main.profile.setting.githubsetting

import androidx.lifecycle.ViewModel
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.info.request.RegisterSocialRequest
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class GithubSettingState(
    val githubId: String = ""
)

sealed interface GithubSettingSideEffect {
    data object PatchGithubSettingSuccess: GithubSettingSideEffect
    data object PatchGithubSettingFailure: GithubSettingSideEffect
}

class GithubSettingViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<GithubSettingSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(GithubSettingState())
    val uiState = _uiState.asStateFlow()

    fun updateGithubId(id: String) {
        _uiState.update { it.copy(githubId = id) }
    }

    fun patchGithubId() {
        launch {
            try {
                val request = RegisterSocialRequest(
                    socialId = _uiState.value.githubId
                )
                RetrofitClient.infoApi.registerGithub(request)
                _uiEffect.emit(GithubSettingSideEffect.PatchGithubSettingSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(GithubSettingSideEffect.PatchGithubSettingFailure)
            }
        }
    }
}