package com.molohala.infinity.ui.main.profile.setting.profileedit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ProfileEditViewModel: ViewModel() {
    data class State(
        val jobs: ArrayList<String> = arrayListOf()
    )

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()
}