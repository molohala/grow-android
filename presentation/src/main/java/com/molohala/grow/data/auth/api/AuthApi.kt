package com.molohala.grow.data.auth.api

import com.molohala.grow.data.auth.response.ReissueResponse
import com.molohala.grow.data.global.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/auth/reissue")
    suspend fun reissue(
        @Body reissueRequest: com.molohala.grow.data.auth.request.ReissueRequest
    ): BaseResponse<ReissueResponse>

    @POST("/auth/sign-in")
    suspend fun signIn(
        @Query("code") code: String
    ): BaseResponse<com.molohala.grow.data.auth.response.TokenResponse>

    @DELETE("/auth")
    suspend fun delete()
}