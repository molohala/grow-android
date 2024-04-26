package com.molohala.infinity.info.response

import java.time.LocalDate

data class CommitResponse(
    val date: LocalDate,
    val contributionCount: Int
)