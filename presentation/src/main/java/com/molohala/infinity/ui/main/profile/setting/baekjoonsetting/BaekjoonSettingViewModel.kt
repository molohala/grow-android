package com.molohala.infinity.ui.main.profile.setting.baekjoonsetting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class BaekjoonSettingViewModel: ViewModel() {
    data class State(
        val baekjoonId: String = ""
    )

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    fun updateBaekjoonId(id: String) {
        _uiState.update { it.copy(baekjoonId = id) }
    }
}