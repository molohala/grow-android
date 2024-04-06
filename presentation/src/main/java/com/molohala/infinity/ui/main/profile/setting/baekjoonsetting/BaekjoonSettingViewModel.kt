package com.molohala.infinity.ui.main.profile.setting.baekjoonsetting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class BaekjoonSettingViewModel: ViewModel() {
    data class State(
        val baekjoonId: String = ""
    )

    var uiState = MutableStateFlow(State())

    fun updateBaekjoonId(id: String) {
        uiState.update { it.copy(baekjoonId = id) }
    }
}