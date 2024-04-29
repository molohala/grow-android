package com.molohala.infinity.data.community.api

import com.molohala.infinity.data.community.response.CommunityResponse
import com.molohala.infinity.data.global.dto.response.BaseResponse
import com.molohala.infinity.data.global.dto.response.BaseVoidResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApi {
    @GET("/community")
    suspend fun getCommunities(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<List<CommunityResponse>>

    @POST("/community")
    suspend fun createCommunity(
        @Body request: com.molohala.infinity.data.community.request.CreateCommunityRequest
    ): BaseVoidResponse

    @PATCH("/community")
    suspend fun patchCommunity(
        @Body request: com.molohala.infinity.data.community.request.PatchCommunityRequest
    ): BaseVoidResponse

    @GET("/community/{id}")
    suspend fun getCommunity(
        @Path("id") id: Int
    ): com.molohala.infinity.data.community.response.CommunityContentResponse

    @DELETE("/community/{id}")
    suspend fun removeCommunity(
        @Path("id") id: Int
    ): BaseVoidResponse
}
