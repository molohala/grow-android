package com.molohala.grow.data.dauth.request

data class DAuthSignInRequest(
    public val id: String,
    public val pw: String,
    public val clientId: String,
    public val redirectUrl: String
)
