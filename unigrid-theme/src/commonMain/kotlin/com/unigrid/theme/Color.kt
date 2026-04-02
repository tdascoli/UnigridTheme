package com.unigrid.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Raw unigrid colors — exact hex values from unigrid.css
val UgBlack = Color(0xFF1A1A1A)
val UgWhite = Color(0xFFFFFFFF)
val UgWarmGray = Color(0xFFF5F2ED)
val UgLightGray = Color(0xFFE8E5E0)
val UgMediumGray = Color(0xFF666666)
val UgDarkGray = Color(0xFF333333)
val UgRed = Color(0xFFC1272D)
val UgBrown = Color(0xFF4A3728)
val UgGreen = Color(0xFF2D5A27)
val UgBlue = Color(0xFF274A5A)

@Immutable
data class UnigridColorPalette(
    val black: Color = UgBlack,
    val white: Color = UgWhite,
    val warmGray: Color = UgWarmGray,
    val lightGray: Color = UgLightGray,
    val mediumGray: Color = UgMediumGray,
    val darkGray: Color = UgDarkGray,
    val red: Color = UgRed,
    val brown: Color = UgBrown,
    val green: Color = UgGreen,
    val blue: Color = UgBlue,
)

val LocalUnigridColors = staticCompositionLocalOf { UnigridColorPalette() }

val UnigridLightColors: ColorScheme = lightColorScheme(
    primary = UgRed,
    onPrimary = UgWhite,
    primaryContainer = UgBlue,
    onPrimaryContainer = UgWhite,
    secondary = UgBrown,
    onSecondary = UgWhite,
    secondaryContainer = UgWarmGray,
    onSecondaryContainer = UgDarkGray,
    tertiary = UgGreen,
    onTertiary = UgWhite,
    tertiaryContainer = UgGreen.copy(alpha = 0.12f),
    onTertiaryContainer = UgGreen,
    background = UgWhite,
    onBackground = UgBlack,
    surface = UgWhite,
    onSurface = UgBlack,
    surfaceVariant = UgWarmGray,
    onSurfaceVariant = UgDarkGray,
    outline = UgMediumGray,
    outlineVariant = UgLightGray,
    error = UgRed,
    onError = UgWhite,
    errorContainer = UgRed.copy(alpha = 0.12f),
    onErrorContainer = UgRed,
)

val UnigridDarkColors: ColorScheme = darkColorScheme(
    primary = UgRed,
    onPrimary = UgWhite,
    primaryContainer = UgBlue,
    onPrimaryContainer = UgWhite,
    secondary = UgBrown,
    onSecondary = UgWhite,
    secondaryContainer = UgDarkGray,
    onSecondaryContainer = UgLightGray,
    tertiary = UgGreen,
    onTertiary = UgWhite,
    tertiaryContainer = UgGreen.copy(alpha = 0.2f),
    onTertiaryContainer = Color(0xFF8FBF8A),
    background = UgBlack,
    onBackground = UgWhite,
    surface = UgBlack,
    onSurface = UgWhite,
    surfaceVariant = UgDarkGray,
    onSurfaceVariant = UgLightGray,
    outline = UgMediumGray,
    outlineVariant = UgDarkGray,
    error = Color(0xFFE57373),
    onError = UgBlack,
    errorContainer = UgRed.copy(alpha = 0.3f),
    onErrorContainer = Color(0xFFE57373),
)
