package com.molohala.infinity.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.global.Secret
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignInState(
    val id: String = "",
    val pw: String = ""
)

sealed interface SignInSideEffect {
    data class LoginSuccess(val accessToken: String, val refreshToken: String): SignInSideEffect
}

class SignInViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<SignInSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    fun signIn() {

        val id = _uiState.value.id
        val pw = _uiState.value.pw

        viewModelScope.launch {
            try {
                val dAuthRequest = com.molohala.infinity.data.dauth.request.DAuthSignInRequest(
                    id = id,
                    pw = pw,
                    clientId = Secret.CLIENT_ID,
                    redirectUrl = Secret.REDIRECT_URL
                )
                val dAuthResponse = RetrofitClient.dauthApi.signIn(dAuthRequest).data

                val code = dAuthResponse.location.split("[=&]".toRegex())[1]
                val response = RetrofitClient.authApi.signIn(code = code).data
                val effect = SignInSideEffect.LoginSuccess(accessToken = response.accessToken, refreshToken = response.refreshToken)
                _uiEffect.emit(effect)
            } catch (e: Exception) {
                Log.d(TAG, "signIn: $e")
            }
        }
    }

    fun updateId(id: String) {
        _uiState.update { it.copy(id = id) }
    }

    fun updatePw(pw: String) {
        _uiState.update { it.copy(pw = pw) }
    }
}