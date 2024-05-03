package com.molohala.grow.designsystem.foundation.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
class GrowTypography(
    val title1B: TextStyle = GrowTypographyTokens.Title1B,
    val title1M: TextStyle = GrowTypographyTokens.Title1M,
    val title1R: TextStyle = GrowTypographyTokens.Title1R,
    val title2B: TextStyle = GrowTypographyTokens.Title2B,
    val title2M: TextStyle = GrowTypographyTokens.Title2M,
    val title2R: TextStyle = GrowTypographyTokens.Title2R,
    val headline1B: TextStyle = GrowTypographyTokens.Headline1B,
    val headline1M: TextStyle = GrowTypographyTokens.Headline1M,
    val headline1R: TextStyle = GrowTypographyTokens.Headline1R,
    val headline2B: TextStyle = GrowTypographyTokens.Headline2B,
    val headline2M: TextStyle = GrowTypographyTokens.Headline2M,
    val headline2R: TextStyle = GrowTypographyTokens.Headline2R,
    val bodyBold: TextStyle = GrowTypographyTokens.BodyBold,
    val bodyMedium: TextStyle = GrowTypographyTokens.BodyMedium,
    val bodyRegular: TextStyle = GrowTypographyTokens.BodyRegular,
    val labelBold: TextStyle = GrowTypographyTokens.LabelBold,
    val labelMedium: TextStyle = GrowTypographyTokens.LabelMedium,
    val labelRegular: TextStyle = GrowTypographyTokens.LabelRegular,
    val captionBold: TextStyle = GrowTypographyTokens.CaptionBold,
    val captionMedium: TextStyle = GrowTypographyTokens.CaptionMedium,
    val captionRegular: TextStyle = GrowTypographyTokens.CaptionRegular,
)

internal val LocalGrowTypography = staticCompositionLocalOf { GrowTypography() }
