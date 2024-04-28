package com.molohala.infinity.auth.api

import com.molohala.infinity.auth.request.ReissueRequest
import com.molohala.infinity.auth.response.ReissueResponse
import com.molohala.infinity.auth.response.TokenResponse
import com.molohala.infinity.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/auth/reissue")
    suspend fun reissue(
        @Body reissueRequest: ReissueRequest
    ): BaseResponse<ReissueResponse>

    @POST("/auth/sign-in")
    suspend fun signIn(
        @Query("code") code: String
    ): BaseResponse<TokenResponse>
}