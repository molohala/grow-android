package com.molohala.infinity.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileState(
    val isRefresh: Boolean = false
)

class ProfileViewModel: ViewModel() {

    val uiState = MutableStateFlow(ProfileState())

    fun refresh() {
        uiState.update { it.copy(isRefresh = false) }
    }
}