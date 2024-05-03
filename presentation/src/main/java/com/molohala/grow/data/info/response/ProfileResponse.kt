package com.molohala.grow.data.info.response

import java.time.LocalDateTime

data class ProfileResponse(
    val id: Int,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val socialAccounts: List<SocialAccountResponse>
)
