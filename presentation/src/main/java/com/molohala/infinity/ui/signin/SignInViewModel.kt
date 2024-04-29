package com.molohala.infinity.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.global.RetrofitClient
import com.molohala.infinity.data.global.Secret
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

    val uiState = MutableStateFlow(SignInState())
    val uiEffect = MutableSharedFlow<SignInSideEffect>()

    fun signIn() {

        val id = uiState.value.id
        val pw = uiState.value.pw

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
                uiEffect.emit(effect)
            } catch (e: Exception) {
                Log.d(TAG, "signIn: $e")
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