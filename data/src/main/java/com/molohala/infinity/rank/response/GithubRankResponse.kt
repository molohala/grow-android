package com.molohala.infinity.rank.response

data class GithubRankResponse(
    val memberId: Int,
    val memberName: String,
    val socialId: String,
    val rank: Int,
    val commits: Int
)