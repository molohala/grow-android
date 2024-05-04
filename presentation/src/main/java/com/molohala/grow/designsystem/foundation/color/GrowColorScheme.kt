package com.molohala.grow.designsystem.foundation.color

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
class GrowColorScheme(
    textNormal: Color,
    textDarken: Color,
    textAlt: Color,
    textInverse: Color,
    textDisabled: Color,
    textWarning: Color,
    buttonPrimary: Color,
    buttonPrimaryPressed: Color,
    buttonPrimaryDisabled: Color,
    buttonText: Color,
    buttonTextDisabled: Color,
    background: Color,
    backgroundAlt: Color,
    backgroundInverse: Color,
    github: Color,
    baekjoon: Color,
    dividerNormal: Color,
    textFieldIcon: Color,
    textFieldPrimary: Color,
    textFieldSecondary: Color,
    textFieldSecondaryDisabled: Color,
    textFieldTextDisabled: Color,
    avatarLabel: Color,
    avatarBackground: Color,
    elevationBlack1: Color,
    elevationBlack2: Color,
    elevationBlack3: Color,
    bottomTabPrimary: Color,
    bottomTabPrimaryDisabled: Color,
    bottomTabSecondary: Color,
    likePrimary: Color,
    likeSecondary: Color
) {
    var textNormal by mutableStateOf(textNormal, structuralEqualityPolicy())
        internal set
    var textDarken by mutableStateOf(textDarken, structuralEqualityPolicy())
        internal set
    var textAlt by mutableStateOf(textAlt, structuralEqualityPolicy())
        internal set
    var textInverse by mutableStateOf(textInverse, structuralEqualityPolicy())
        internal set
    var textDisabled by mutableStateOf(textDisabled, structuralEqualityPolicy())
        internal set
    var textWarning by mutableStateOf(textWarning, structuralEqualityPolicy())
        internal set
    var buttonPrimary by mutableStateOf(buttonPrimary, structuralEqualityPolicy())
        internal set
    var buttonPrimaryPressed by mutableStateOf(buttonPrimaryPressed, structuralEqualityPolicy())
        internal set
    var buttonPrimaryDisabled by mutableStateOf(buttonPrimaryDisabled, structuralEqualityPolicy())
        internal set
    var buttonText by mutableStateOf(buttonText, structuralEqualityPolicy())
        internal set
    var buttonTextDisabled by mutableStateOf(buttonTextDisabled, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var backgroundAlt by mutableStateOf(backgroundAlt, structuralEqualityPolicy())
        internal set
    var backgroundInverse by mutableStateOf(backgroundInverse, structuralEqualityPolicy())
        internal set
    var github by mutableStateOf(github, structuralEqualityPolicy())
        internal set
    var baekjoon by mutableStateOf(baekjoon, structuralEqualityPolicy())
        internal set
    var dividerNormal by mutableStateOf(dividerNormal, structuralEqualityPolicy())
        internal set
    var textFieldIcon by mutableStateOf(textFieldIcon, structuralEqualityPolicy())
        internal set
    var textFieldPrimary by mutableStateOf(textFieldPrimary, structuralEqualityPolicy())
        internal set
    var textFieldSecondary by mutableStateOf(textFieldSecondary, structuralEqualityPolicy())
        internal set
    var textFieldSecondaryDisabled by mutableStateOf(textFieldSecondaryDisabled, structuralEqualityPolicy())
        internal set
    var textFieldTextDisabled by mutableStateOf(textFieldTextDisabled, structuralEqualityPolicy())
        internal set
    var avatarLabel by mutableStateOf(avatarLabel, structuralEqualityPolicy())
        internal set
    var avatarBackground by mutableStateOf(avatarBackground, structuralEqualityPolicy())
        internal set
    var elevationBlack1 by mutableStateOf(elevationBlack1, structuralEqualityPolicy())
        internal set
    var elevationBlack2 by mutableStateOf(elevationBlack2, structuralEqualityPolicy())
        internal set
    var elevationBlack3 by mutableStateOf(elevationBlack3, structuralEqualityPolicy())
        internal set
    var bottomTabPrimary by mutableStateOf(bottomTabPrimary, structuralEqualityPolicy())
        internal set
    var bottomTabPrimaryDisabled by mutableStateOf(bottomTabPrimaryDisabled, structuralEqualityPolicy())
        internal set
    var bottomTabSecondary by mutableStateOf(bottomTabSecondary, structuralEqualityPolicy())
        internal set
    var likePrimary by mutableStateOf(likePrimary, structuralEqualityPolicy())
        internal set
    var likeSecondary by mutableStateOf(likeSecondary, structuralEqualityPolicy())
        internal set
}

internal val LocalGrowColorScheme = staticCompositionLocalOf { lightColorScheme() }
