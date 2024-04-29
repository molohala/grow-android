package com.molohala.infinity.data.info.response

data class SolvedacResponse(
    val avatarUrl: String,
    val bio: String,
    val maxStreak: Int,
    val name: String,
    val rating: Int,
    val tier: Int,
    val todaySolves: SolveResponse,
    val totalRank: Int,
    val totalSolves: Int,
    val weekSolves: List<SolveResponse>
)