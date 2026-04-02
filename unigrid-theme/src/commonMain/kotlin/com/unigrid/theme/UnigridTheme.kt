package com.unigrid.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun UnigridTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) UnigridDarkColors else UnigridLightColors

    CompositionLocalProvider(
        LocalUnigridColors provides UnigridColorPalette(),
        LocalUnigridSpacing provides UnigridSpacing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = UnigridTypography(),
            shapes = UnigridShapes,
            content = content,
        )
    }
}

object UnigridTheme {
    val colors: UnigridColorPalette
        @Composable @ReadOnlyComposable
        get() = LocalUnigridColors.current

    val spacing: UnigridSpacing
        @Composable @ReadOnlyComposable
        get() = LocalUnigridSpacing.current
}
