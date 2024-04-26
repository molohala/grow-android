package com.molohala.infinity.info.response

import java.time.LocalDate

data class SolveResponse(
    val date: LocalDate,
    val solvedCount: Int,
    val keepStreakReason: String
)