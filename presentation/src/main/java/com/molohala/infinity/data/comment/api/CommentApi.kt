package com.molohala.infinity.data.comment.api

import com.molohala.infinity.data.comment.response.CommentResponse
import com.molohala.infinity.data.global.dto.response.BaseResponse
import com.molohala.infinity.data.global.dto.response.BaseVoidResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET("/comment")
    suspend fun getComment(
        @Query("communityId") communityId: Int
    ): BaseResponse<List<CommentResponse>>

    @POST("/comment")
    suspend fun createComment(
        @Body request: com.molohala.infinity.data.comment.request.CreateCommentRequest
    ): BaseVoidResponse

    @PATCH("/comment")
    suspend fun patchComment(
        @Body request: com.molohala.infinity.data.comment.request.PatchCommentRequest
    ): BaseVoidResponse

    @DELETE("/comment/{id}")
    suspend fun removeComment(
        @Path("id") id: Int
    ): BaseVoidResponse
}