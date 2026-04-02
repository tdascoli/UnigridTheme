package com.unigrid.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class UnigridSpacing(
    val level0: Dp = 0.dp,
    val level1: Dp = 6.5.dp,   // 0.25x leading
    val level2: Dp = 13.dp,    // 0.5x leading
    val level3: Dp = 26.dp,    // 1x leading (base)
    val level4: Dp = 39.dp,    // 1.5x leading
    val level5: Dp = 52.dp,    // 2x leading
    val level6: Dp = 78.dp,    // 3x leading
    val level7: Dp = 104.dp,   // 4x leading
    val level8: Dp = 156.dp,   // 6x leading
)

val LocalUnigridSpacing = staticCompositionLocalOf { UnigridSpacing() }
