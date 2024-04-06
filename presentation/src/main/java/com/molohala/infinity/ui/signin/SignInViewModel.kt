package com.molohala.infinity.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.DodamSignInRequest
import com.molohala.infinity.RetrofitClient
import com.molohala.infinity.ui.Secret
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest


class SignInViewModel: ViewModel() {
    data class SignInState(
        val id: String = "",
        val pw: String = ""
    )

    val uiState = MutableStateFlow(SignInState())

    fun signIn() {

        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest(uiState.value.pw.toByteArray())
        val sb = StringBuilder()
        for (i in digest.indices) {
            sb.append(((digest[i].toInt() and 0xff) + 0x100).toString(16).substring(1))
        }
        println(sb)

        viewModelScope.launch {
            try {
                val request = DodamSignInRequest(
                    id = uiState.value.id,
                    pw = sb.toString(),
                    clientId = Secret.CLIENT_ID,
                    redirectUrl = Secret.REDIRECT_URL
                )
                val response = RetrofitClient.dodamApi.signIn(request)
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