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
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoApi {

    @GET("/info/solvedac")
    suspend fun getSolvedacInfo(
        @Query("name") name: String
    ): BaseResponse<SolvedacResponse>

    @POST("/info/solvedac")
    suspend fun registerSolvedac(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/github")
    suspend fun getGithubInfo(
        @Query("name") name: String
    ): BaseResponse<GithubResponse>

    @POST("/info/github")
    suspend fun registerGithub(
        @Body request: RegisterSocialRequest
    ): BaseVoidResponse

    @GET("/info/me")
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    @GET("/info/user/{id}")
    suspend fun getProfileById(
        @Path("id") id: Int
    ): BaseResponse<ProfileResponse>
}