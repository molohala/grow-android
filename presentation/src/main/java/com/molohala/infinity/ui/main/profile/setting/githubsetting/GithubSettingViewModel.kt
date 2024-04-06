package com.molohala.infinity.ui.main.profile.setting.githubsetting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class GithubSettingViewModel: ViewModel() {
    data class State(
        val githubId: String = ""
    )

    var uiState = MutableStateFlow(State())

    fun updateGithubId(id: String) {
        uiState.update { it.copy(githubId = id) }
    }
}