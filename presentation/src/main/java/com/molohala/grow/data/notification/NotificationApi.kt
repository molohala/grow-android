package com.molohala.grow.data.notification

import com.molohala.grow.data.global.dto.response.BaseVoidResponse
import retrofit2.http.POST

interface NotificationApi {
    @POST("/notification")
    suspend fun postFcmToken(
        req: PostFcmTokenReq
    ): BaseVoidResponse
}