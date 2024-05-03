package com.molohala.grow.data.auth.response

data class TokenResponse(
    val refreshToken: String,
    val accessToken: String
)
