package com.molohala.grow.ui.main.profile.setting.profileedit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileEditState(
    val jobs: ArrayList<String> = arrayListOf()
)

class ProfileEditViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileEditState())
    val uiState = _uiState.asStateFlow()
}