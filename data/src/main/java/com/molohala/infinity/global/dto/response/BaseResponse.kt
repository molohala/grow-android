package com.molohala.infinity.global.dto.response

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)

data class BaseVoidResponse(
    val status: Int,
    val message: String,
)