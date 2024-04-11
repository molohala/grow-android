package com.molohala.infinity.auth.api

import com.molohala.infinity.auth.request.ReissueRequest
import com.molohala.infinity.auth.request.SignInRequest
import com.molohala.infinity.auth.response.ReissueResponse
import com.molohala.infinity.auth.response.TokenResponse
import com.molohala.infinity.global.dto.response.BaseResponse
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/reissue")
    fun reissue(
        reissueRequest: ReissueRequest
    ): BaseResponse<ReissueResponse>

    @POST("/auth/sign-in")
    fun signIn(
        signInRequest: SignInRequest
    ): BaseResponse<TokenResponse>
}