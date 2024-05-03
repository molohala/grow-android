package com.molohala.grow.data.like.api

import com.molohala.grow.data.global.dto.response.BaseVoidResponse
import retrofit2.http.PATCH
import retrofit2.http.Path

interface LikeApi {

    @PATCH("/like/{id}")
    fun patchLike(
        @Path("id") id: Int
    ): BaseVoidResponse

}