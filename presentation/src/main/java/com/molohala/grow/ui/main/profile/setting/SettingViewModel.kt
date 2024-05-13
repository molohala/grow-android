package com.molohala.grow.ui.main.profile.setting

import androidx.lifecycle.ViewModel
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed interface SettingSideEffect {
    data object RemoveMemberFailure: SettingSideEffect
    data object RemoveMemberSuccess: SettingSideEffect
}

class SettingViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<SettingSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun removeMember() {
        launch {
            try {
                RetrofitClient.authApi.delete()
                _uiEffect.emit(SettingSideEffect.RemoveMemberSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(SettingSideEffect.RemoveMemberFailure)
            }
        }
    }
}