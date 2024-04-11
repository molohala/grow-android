package com.molohala.infinity.ui.main.statcell

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.molohala.infinity.R
import com.molohala.infinity.color.InfinityColor

sealed class InfinityStatType(
    @DrawableRes val icon: Int,
    val iconColor: Color
) {
    data class Github(val commit: Int): InfinityStatType(icon = R.drawable.ic_github, iconColor = InfinityColor.github)
    data class Baekjoon(val solved: Int): InfinityStatType(icon = R.drawable.ic_baekjoon, iconColor = InfinityColor.baekjoon)
}