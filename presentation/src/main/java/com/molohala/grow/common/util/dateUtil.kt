package com.molohala.grow.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.updatedAt() = this.format(DateTimeFormatter.ofPattern("MM/dd HH:mm 기준"))
