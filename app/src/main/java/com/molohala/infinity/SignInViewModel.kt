package com.molohala.infinity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class SignInViewModel: ViewModel() {
    data class SignInState(
        var id: String = "",
        var pw: String = ""
    )

    val uiState = MutableStateFlow(SignInState())

    fun signIn() {

    }
}