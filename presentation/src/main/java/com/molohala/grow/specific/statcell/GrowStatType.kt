package com.molohala.grow.specific.statcell

import androidx.annotation.DrawableRes
import com.bestswlkh0310.mydesignsystem.R

sealed class GrowStatType(
    @DrawableRes val icon: Int,
) {
    data class Github(val commit: Int? = null) : GrowStatType(icon = R.drawable.ic_github)
    data class Baekjoon(val solved: Int? = null) : GrowStatType(icon = R.drawable.ic_baekjoon)
}