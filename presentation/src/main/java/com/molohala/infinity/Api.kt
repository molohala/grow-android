package com.molohala.infinity

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DodamApi {
    @POST("/api/auth/login/")
    suspend fun signIn(
        @Body loginRequest: DodamSignInRequest
    ): DodamSignInResponse
}