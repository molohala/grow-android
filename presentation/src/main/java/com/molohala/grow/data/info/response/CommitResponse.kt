package com.molohala.grow.data.info.response

import java.time.LocalDate

data class CommitResponse(
    val date: LocalDate,
    val contributionCount: Int
)