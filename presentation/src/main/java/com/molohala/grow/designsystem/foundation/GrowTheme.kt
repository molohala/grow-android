package com.molohala.grow.designsystem.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.molohala.grow.designsystem.foundation.color.GrowColorScheme
import com.molohala.grow.designsystem.foundation.color.LocalGrowColorScheme
import com.molohala.grow.designsystem.foundation.typography.GrowTypography
import com.molohala.grow.designsystem.foundation.typography.LocalGrowTypography

object GrowTheme {
    val colorScheme: GrowColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalGrowColorScheme.current

    val typography: GrowTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalGrowTypography.current
}