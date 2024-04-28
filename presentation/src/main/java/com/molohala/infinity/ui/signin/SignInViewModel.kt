package com.molohala.infinity.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.application.InfinityApp
import com.molohala.infinity.dauth.request.DAuthSignInRequest
import com.molohala.infinity.global.RetrofitClient
import com.molohala.infinity.global.Secret
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignInState(
    val id: String = "",
    val pw: String = ""
)

sealed interface SignInSideEffect {
    data object Success : SignInSideEffect
}

class SignInViewModel: ViewModel() {

    val uiState = MutableStateFlow(SignInState())
    val uiEffect = MutableSharedFlow<SignInSideEffect>()

    fun signIn() {

        val id = uiState.value.id
        val pw = uiState.value.pw

        viewModelScope.launch {
            try {
                val dAuthRequest = DAuthSignInRequest(
                    id = id,
                    pw = pw,
                    clientId = Secret.CLIENT_ID,
                    redirectUrl = Secret.REDIRECT_URL
                )
                val dAuthResponse = RetrofitClient.dauthApi.signIn(dAuthRequest).data

                val code = dAuthResponse.location.split("[=&]".toRegex())[1]
                val response = RetrofitClient.authApi.signIn(code = code).data

                InfinityApp.prefs.apply {
                    this.accessToken = response.accessToken
                    this.refreshToken = response.refreshToken
                }

                uiEffect.emit(SignInSideEffect.Success)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun updateId(id: String) {
        uiState.update { it.copy(id = id) }
    }

    fun updatePw(pw: String) {
        uiState.update { it.copy(pw = pw) }
    }
}