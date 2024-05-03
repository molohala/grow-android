package com.molohala.grow.ui.main.profile.setting.githubsetting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GithubSettingViewModel: ViewModel() {
    data class State(
        val githubId: String = ""
    )

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    fun updateGithubId(id: String) {
        _uiState.update { it.copy(githubId = id) }
    }
}