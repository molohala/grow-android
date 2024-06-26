package com.molohala.grow.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.global.Secret
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignInState(
    val id: String = "",
    val pw: String = "",
    val isFetching: Boolean = false
)

sealed interface SignInSideEffect {
    data class LoginSuccess(val accessToken: String, val refreshToken: String): SignInSideEffect
    data object LoginFailure: SignInSideEffect
}

class SignInViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<SignInSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    fun signIn() {

        val id = _uiState.value.id
        val pw = _uiState.value.pw

        _uiState.update { it.copy(isFetching = true) }

        launch {
            try {
                val dAuthRequest = com.molohala.grow.data.dauth.request.DAuthSignInRequest(
                    id = id,
                    pw = pw,
                    clientId = Secret.CLIENT_ID,
                    redirectUrl = Secret.REDIRECT_URL
                )
                val dAuthResponse = RetrofitClient.dauthApi.signIn(dAuthRequest).data?: return@launch

                val code = dAuthResponse.location.split("[=&]".toRegex())[1]
                val response = RetrofitClient.authApi.signIn(code = code).data?: return@launch
                val effect = SignInSideEffect.LoginSuccess(accessToken = response.accessToken, refreshToken = response.refreshToken)
                _uiEffect.emit(effect)
            } catch (e: Exception) {
                Log.d(TAG, "signIn: $e")
                _uiEffect.emit(SignInSideEffect.LoginFailure)
            } finally {
                _uiState.update { it.copy(isFetching = false) }
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