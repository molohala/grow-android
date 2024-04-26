package com.molohala.infinity.community.api

import com.molohala.infinity.community.request.CreateCommunityRequest
import com.molohala.infinity.community.request.PatchCommunityRequest
import com.molohala.infinity.community.response.CommunityContentResponse
import com.molohala.infinity.community.response.CommunityResponse
import com.molohala.infinity.global.dto.response.BaseResponse
import com.molohala.infinity.global.dto.response.BaseVoidResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApi {
    @GET("/community")
    fun getCommunities(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<List<CommunityResponse>>

    @POST("/community")
    fun createCommunity(
        @Body request: CreateCommunityRequest
    ): BaseVoidResponse

    @PATCH("/community")
    fun patchCommunity(
        @Body request: PatchCommunityRequest
    ): BaseVoidResponse

    @GET("/community/{id}")
    fun getCommunity(
        @Path("id") id: Int
    ): CommunityContentResponse

    @DELETE("/community/{id}")
    fun removeCommunity(
        @Path("id") id: Int
    ): BaseVoidResponse
}
