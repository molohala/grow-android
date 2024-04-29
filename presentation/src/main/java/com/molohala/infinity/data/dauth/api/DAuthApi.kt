package com.molohala.infinity.data.dauth.api

import com.molohala.infinity.data.dauth.response.DAuthSignInResponse
import com.molohala.infinity.data.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DAuthApi {
    @POST("/api/auth/login/")
    suspend fun signIn(
        @Body loginRequest: com.molohala.infinity.data.dauth.request.DAuthSignInRequest
    ): BaseResponse<DAuthSignInResponse>
}