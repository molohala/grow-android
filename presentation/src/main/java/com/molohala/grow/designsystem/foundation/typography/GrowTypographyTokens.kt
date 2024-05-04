package com.molohala.grow.designsystem.foundation.typography

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object GrowTypographyTokens {

    /*
    Title1 Typography
     */
    val Title1B = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    )
    val Title1M = Title1B.copy(fontWeight = FontWeight.Medium)
    val Title1R = Title1B.copy(fontWeight = FontWeight.Normal)

    /*
    Title2 Typography
     */
    val Title2B = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )
    val Title2M = Title2B.copy(fontWeight = FontWeight.Medium)
    val Title2R = Title2B.copy(fontWeight = FontWeight.Normal)

    /*
    Headline1 Typography
     */
    val Headline1B = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    )
    val Headline1M = Headline1B.copy(fontWeight = FontWeight.Medium)
    val Headline1R = Headline1B.copy(fontWeight = FontWeight.Normal)

    /*
    Headline2 Typography
     */
    val Headline2B = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )
    val Headline2M = Headline2B.copy(fontWeight = FontWeight.Medium)
    val Headline2R = Headline2B.copy(fontWeight = FontWeight.Normal)

    /*
    Body Typography
     */
    val BodyBold = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )
    val BodyMedium = BodyBold.copy(fontWeight = FontWeight.Medium)
    val BodyRegular = BodyBold.copy(fontWeight = FontWeight.Normal)

    /*
    Label Typography
     */
    val LabelBold = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )
    val LabelMedium = LabelBold.copy(fontWeight = FontWeight.Medium)
    val LabelRegular = LabelBold.copy(fontWeight = FontWeight.Normal)

    /*
    Caption Typography
     */
    val CaptionBold = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )
    val CaptionMedium = CaptionBold.copy(fontWeight = FontWeight.Medium)
    val CaptionRegular = CaptionBold.copy(fontWeight = FontWeight.Normal)
}

val DefaultTextStyle = TextStyle.Default.copy(
    fontFamily = FontFamily.SansSerif,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeight = 1.3.em
)