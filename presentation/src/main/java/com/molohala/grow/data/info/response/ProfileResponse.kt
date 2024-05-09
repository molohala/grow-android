package com.molohala.grow.data.info.response

import java.time.LocalDateTime

data class ProfileResponse(
    val id: Int,
    val email: String,
    val name: String,
    val bio: String,
    val job: String,
    val createdAt: LocalDateTime,
    val socialAccounts: List<SocialAccountResponse>
) {
    fun getGithubId(): String? {
        return socialAccounts.firstOrNull { it.socialType == "GITHUB" }?.socialId
    }

    fun getBaekjoonId(): String? {
        return socialAccounts.firstOrNull { it.socialType == "SOLVED_AC" }?.socialId
    }
}
