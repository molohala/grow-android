package com.bestswlkh0310.designsystem.foundation.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

val LocalDateTime.timeAgo: String
    get() {
        val currentDate = LocalDateTime.now()
        val years = ChronoUnit.YEARS.between(this, currentDate)
        if (years > 0) {
            return "${years}년 전"
        }

        val months = ChronoUnit.MONTHS.between(this, currentDate)
        if (months > 0) {
            return "${months}개월 전"
        }

        val days = ChronoUnit.DAYS.between(this, currentDate)
        if (days > 0) {
            return "${days}일 전"
        }

        val hours = ChronoUnit.HOURS.between(this, currentDate)
        if (hours > 0) {
            return "${hours}시간 전"
        }

        val minutes = ChronoUnit.MINUTES.between(this, currentDate)
        if (minutes > 0) {
            return "${minutes}분 전"
        }

        return "방금 전"
    }

val LocalDate.monthPerDay: String
    get() {
        val dateFormatter = DateTimeFormatter.ofPattern("MM/dd")
        return this.format(dateFormatter)
    }


val datesForWeek: List<LocalDate>
    get() {
        val datesInWeek: MutableList<LocalDate> = ArrayList()
        val currentDate = LocalDate.now()
        var startDate = currentDate
        repeat(7) {
            datesInWeek.add(startDate)
            startDate = startDate.minus(1, ChronoUnit.DAYS)
        }
        return datesInWeek.reversed()
    }