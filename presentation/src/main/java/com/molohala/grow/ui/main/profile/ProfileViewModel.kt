package com.molohala.grow.ui.main.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileState(
    val isRefresh: Boolean = false
)

class ProfileViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    fun refresh() {
        _uiState.update { it.copy(isRefresh = false) }
    }
}