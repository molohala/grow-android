package com.molohala.grow.designsystem.legacy.statcell

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.molohala.grow.R
import com.molohala.grow.designsystem.color.GrowColor

sealed class InfinityStatType(
    @DrawableRes val icon: Int,
    val iconColor: Color
) {
    data class Github(val commit: Int? = null): InfinityStatType(icon = R.drawable.ic_github, iconColor = GrowColor.github)
    data class Baekjoon(val solved: Int? = null): InfinityStatType(icon = R.drawable.ic_baekjoon, iconColor = GrowColor.baekjoon)
}