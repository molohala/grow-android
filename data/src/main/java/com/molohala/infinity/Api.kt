package com.molohala.infinity

import retrofit2.http.Body
import retrofit2.http.POST

interface DodamApi {
    @POST("/api/auth/login/")
    suspend fun signIn(
        @Body loginRequest: DodamSignInRequest
    ): DodamSignInResponse
}