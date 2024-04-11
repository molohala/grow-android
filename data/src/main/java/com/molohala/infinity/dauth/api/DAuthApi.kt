package com.molohala.infinity.dauth.api

import com.molohala.infinity.dauth.request.DAuthSignInRequest
import com.molohala.infinity.dauth.response.DAuthSignInResponse
import com.molohala.infinity.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DAuthApi {
    @POST("/api/auth/login/")
    suspend fun signIn(
        @Body loginRequest: DAuthSignInRequest
    ): BaseResponse<DAuthSignInResponse>
}