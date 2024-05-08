package com.molohala.grow.data.forum.api

import com.molohala.grow.data.forum.request.CreateForumRequest
import com.molohala.grow.data.forum.request.PatchForumRequest
import com.molohala.grow.data.forum.response.ForumContentResponse
import com.molohala.grow.data.forum.response.ForumResponse
import com.molohala.grow.data.global.dto.response.BaseResponse
import com.molohala.grow.data.global.dto.response.BaseVoidResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumApi {
    @GET("/community")
    suspend fun getForums(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<List<ForumResponse>>

    @POST("/community")
    suspend fun createForum(
        @Body request: CreateForumRequest
    ): BaseVoidResponse

    @PATCH("/community")
    suspend fun patchForum(
        @Body request: PatchForumRequest
    ): BaseVoidResponse

    @GET("/community/{id}")
    suspend fun getForum(
        @Path("id") id: Int
    ): BaseResponse<ForumContentResponse>

    @DELETE("/community/{id}")
    suspend fun removeForum(
        @Path("id") id: Int
    ): BaseVoidResponse

    @GET("/community/best")
    suspend fun getBestForums(
        @Query("count") count: Int = 3
    ): BaseResponse<List<ForumResponse>>
}
