package com.molohala.infinity.data.rank.response

data class GithubRankResponse(
    val memberId: Int,
    val memberName: String,
    val socialId: String,
    val rank: Int,
    val commits: Int
)