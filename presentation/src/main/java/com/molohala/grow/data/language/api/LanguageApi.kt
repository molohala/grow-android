package com.molohala.grow.data.language.api

import com.molohala.grow.data.global.dto.response.BaseResponse
import com.molohala.grow.data.global.dto.response.BaseVoidResponse
import com.molohala.grow.data.language.request.PatchLanguageRequest
import com.molohala.grow.data.language.response.LanguageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface LanguageApi {

    @GET("/language/me")
    suspend fun getMyLanguage(): BaseResponse<List<LanguageResponse>>

    @PATCH("/language")
    suspend fun patchLanguage(
        @Body request: PatchLanguageRequest
    ): BaseVoidResponse

    @GET("/language")
    suspend fun getLanguage(): BaseResponse<List<LanguageResponse>>

}