package com.molohala.infinity.data.info.api

import com.molohala.infinity.data.global.dto.response.BaseResponse
import com.molohala.infinity.data.global.dto.response.BaseVoidResponse
import com.molohala.infinity.data.info.request.RegisterSocialRequest
import com.molohala.infinity.data.info.response.GithubResponse
import com.molohala.infinity.data.info.response.ProfileResponse
import com.molohala.infinity.data.info.response.SolvedacResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InfoApi {

    @GET("/info/solvedac")
    suspend fun getSolvedacInfo(): BaseResponse<SolvedacResponse>

    @POST("/info/solvedac")
    suspend fun registerSolvedac(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/github")
    suspend fun getGithubInfo(): BaseResponse<GithubResponse>

    @POST("/info/github")
    suspend fun registerGithub(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/me")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}