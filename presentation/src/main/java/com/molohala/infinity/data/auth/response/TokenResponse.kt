package com.molohala.infinity.data.auth.response

data class TokenResponse(
    val refreshToken: String,
    val accessToken: String
)
