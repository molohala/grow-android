package com.molohala.infinity.data.auth.api

import com.molohala.infinity.data.auth.response.ReissueResponse
import com.molohala.infinity.data.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/auth/reissue")
    suspend fun reissue(
        @Body reissueRequest: com.molohala.infinity.data.auth.request.ReissueRequest
    ): BaseResponse<ReissueResponse>

    @POST("/auth/sign-in")
    suspend fun signIn(
        @Query("code") code: String
    ): BaseResponse<com.molohala.infinity.data.auth.response.TokenResponse>
}