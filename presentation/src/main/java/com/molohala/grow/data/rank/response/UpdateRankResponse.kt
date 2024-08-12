package com.molohala.grow.data.rank.response

import java.time.LocalDateTime

data class UpdateRankResponse(
    val updatedAt: LocalDateTime,
    val ranks: List<RankResponse>
)
