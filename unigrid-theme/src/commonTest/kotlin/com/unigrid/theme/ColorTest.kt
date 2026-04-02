package com.unigrid.theme

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun unigridBlackMatchesCssValue() {
        assertEquals(Color(0xFF1A1A1A), UgBlack)
    }

    @Test
    fun unigridRedMatchesCssValue() {
        assertEquals(Color(0xFFC1272D), UgRed)
    }

    @Test
    fun unigridWarmGrayMatchesCssValue() {
        assertEquals(Color(0xFFF5F2ED), UgWarmGray)
    }

    @Test
    fun lightColorSchemeHasCorrectPrimary() {
        assertEquals(UgRed, UnigridLightColors.primary)
    }

    @Test
    fun darkColorSchemeHasCorrectBackground() {
        assertEquals(UgBlack, UnigridDarkColors.background)
    }
}
