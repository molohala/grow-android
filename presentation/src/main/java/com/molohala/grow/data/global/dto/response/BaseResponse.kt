package com.molohala.grow.data.global.dto.response

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
)

data class BaseVoidResponse(
    val status: Int,
    val message: String,
)