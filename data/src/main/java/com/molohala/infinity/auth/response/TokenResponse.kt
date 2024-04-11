package com.molohala.infinity.auth.response

data class TokenResponse(
    val refreshToken: String,
    val accessToken: String
)
