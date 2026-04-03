package com.unigrid.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import unigridtheme.unigrid_theme.generated.resources.Res
import unigridtheme.unigrid_theme.generated.resources.inter_black
import unigridtheme.unigrid_theme.generated.resources.inter_bold
import unigridtheme.unigrid_theme.generated.resources.inter_extrabold
import unigridtheme.unigrid_theme.generated.resources.inter_light
import unigridtheme.unigrid_theme.generated.resources.inter_medium
import unigridtheme.unigrid_theme.generated.resources.inter_regular
import unigridtheme.unigrid_theme.generated.resources.inter_semibold
import unigridtheme.unigrid_theme.generated.resources.inter_thin

@Composable
fun InterFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_thin, FontWeight.Thin),
    Font(Res.font.inter_light, FontWeight.Light),
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
    Font(Res.font.inter_extrabold, FontWeight.ExtraBold),
    Font(Res.font.inter_black, FontWeight.Black),
)

@Composable
fun UnigridTypography(): Typography {
    val inter = InterFontFamily()
    val fontFeatures = "liga, calt, ss07"
    return Typography(
        displayLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Black,
            fontSize = 44.sp,
            lineHeight = 52.sp,
            letterSpacing = (-0.35).sp,
            fontFeatureSettings = fontFeatures
        ),
        displayMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 31.sp,
            lineHeight = 38.sp,
            fontFeatureSettings = fontFeatures
        ),
        displaySmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            lineHeight = 32.sp,
            fontFeatureSettings = fontFeatures
        ),
        headlineLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontFeatureSettings = fontFeatures
        ),
        headlineMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFeatureSettings = fontFeatures
        ),
        headlineSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontFeatureSettings = fontFeatures
        ),
        titleLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFeatureSettings = fontFeatures
        ),
        titleMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontFeatureSettings = fontFeatures
        ),
        titleSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontFeatureSettings = fontFeatures
        ),
        bodyLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            fontFeatureSettings = fontFeatures
        ),
        bodyMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            fontFeatureSettings = fontFeatures
        ),
        bodySmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            fontFeatureSettings = fontFeatures
        ),
        labelLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontFeatureSettings = fontFeatures
        ),
        labelMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFeatureSettings = fontFeatures
        ),
        labelSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontFeatureSettings = fontFeatures
        ),
    )
}
