package com.molohala.infinity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class ProfileEditViewModel: ViewModel() {
    data class State(
        val githubId: String = "",
        val baekjoonId: String = ""
    )

    var uiState = MutableStateFlow(State())

    fun updateGithubId(id: String) {
        uiState.update { it.copy(githubId = id) }
    }

    fun updateBaekjoonId(id: String) {
        uiState.update { it.copy(baekjoonId = id) }
    }
}