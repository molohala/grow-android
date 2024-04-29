package com.molohala.infinity.data.info.response

import java.time.LocalDate

data class SolveResponse(
    val date: LocalDate,
    val solvedCount: Int,
    val keepStreakReason: String
)