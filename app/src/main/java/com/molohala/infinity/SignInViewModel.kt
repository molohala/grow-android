package com.molohala.infinity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class SignInViewModel: ViewModel() {
    data class SignInState(
        val id: String = "",
        val pw: String = ""
    )

    val uiState = MutableStateFlow(SignInState())

    fun signIn() {

    }

    fun updateId(id: String) {
        uiState.update { it.copy(id = id) }
    }

    fun updatePw(pw: String) {
        uiState.update { it.copy(pw = pw) }
    }
}