package com.molohala.infinity.info.api

import com.molohala.infinity.global.dto.response.BaseResponse
import com.molohala.infinity.global.dto.response.BaseVoidResponse
import com.molohala.infinity.info.request.RegisterSocialRequest
import com.molohala.infinity.info.response.GithubResponse
import com.molohala.infinity.info.response.ProfileResponse
import com.molohala.infinity.info.response.SolvedacResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InfoApi {

    @GET("/info/solvedac")
    fun getSolvedacInfo(): BaseResponse<SolvedacResponse>

    @POST("/info/solvedac")
    fun registerSolvedac(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/github")
    fun getGithubInfo(): BaseResponse<GithubResponse>

    @POST("/info/github")
    fun registerGithub(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/me")
    fun getProfile(): BaseResponse<ProfileResponse>
}