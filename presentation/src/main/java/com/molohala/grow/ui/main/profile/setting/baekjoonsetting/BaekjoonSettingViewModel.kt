package com.molohala.grow.ui.main.profile.setting.baekjoonsetting

import androidx.lifecycle.ViewModel
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.info.request.RegisterSocialRequest
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BaekjoonSettingState(
    val baekjoonId: String = ""
)

sealed interface BaekjoonSettingSideEffect {
    data object PatchBaekjoonSettingSuccess: BaekjoonSettingSideEffect
    data object PatchBaekjoonSettingFailure: BaekjoonSettingSideEffect
}

class BaekjoonSettingViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<BaekjoonSettingSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(BaekjoonSettingState())
    val uiState = _uiState.asStateFlow()

    fun updateBaekjoonId(id: String) {
        _uiState.update { it.copy(baekjoonId = id) }
    }

    fun patchGithubId() {
        launch {
            try {
                val request = RegisterSocialRequest(
                    socialId = _uiState.value.baekjoonId
                )
                RetrofitClient.infoApi.registerSolvedac(request)
                _uiEffect.emit(BaekjoonSettingSideEffect.PatchBaekjoonSettingSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(BaekjoonSettingSideEffect.PatchBaekjoonSettingFailure)
            }
        }
    }
}