package com.molohala.grow.data.rank.response

data class RankResponse(
    val memberId: Int,
    val memberName: String,
    val socialId: String,
    val rank: Int,
    val count: Int
)