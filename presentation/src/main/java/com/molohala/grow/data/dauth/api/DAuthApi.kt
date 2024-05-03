package com.molohala.grow.data.dauth.api

import com.molohala.grow.data.dauth.response.DAuthSignInResponse
import com.molohala.grow.data.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DAuthApi {
    @POST("/api/auth/login/")
    suspend fun signIn(
        @Body loginRequest: com.molohala.grow.data.dauth.request.DAuthSignInRequest
    ): BaseResponse<DAuthSignInResponse>
}