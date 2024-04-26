package com.molohala.infinity.like.api

import com.molohala.infinity.global.dto.response.BaseVoidResponse
import retrofit2.http.PATCH
import retrofit2.http.Path

interface LikeApi {

    @PATCH("/like/{id}")
    fun patchLike(
        @Path("id") id: Int
    ): BaseVoidResponse

}