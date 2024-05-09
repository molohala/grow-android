package com.molohala.grow.data.language.api

import com.molohala.grow.data.global.dto.response.BaseResponse
import com.molohala.grow.data.global.dto.response.BaseVoidResponse
import com.molohala.grow.data.language.request.PatchMyLanguageRequest
import com.molohala.grow.data.language.response.LanguageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface LanguageApi {

    @GET("/language/me")
    suspend fun getMyLanguage(): BaseResponse<List<LanguageResponse>>

    @PATCH("/language/me")
    suspend fun patchLanguage(
        @Body request: PatchMyLanguageRequest
    ): BaseVoidResponse

    @GET("/language")
    suspend fun getLanguage(): BaseResponse<List<LanguageResponse>>

    @GET("/language/{id}")
    suspend fun getLanguageByMemberId(
        @Path("id") id: Int
    ): BaseResponse<List<LanguageResponse>>
}