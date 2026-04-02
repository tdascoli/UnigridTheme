package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridFooter(
    modifier: Modifier = Modifier,
    dark: Boolean = true,
    compact: Boolean = false,
    content: @Composable () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val bgColor = if (dark) UgBlack else UgWarmGray
    val vPadding = if (compact) spacing.level3 else spacing.level6
    val hPadding = spacing.level4

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(horizontal = hPadding, vertical = vPadding),
    ) {
        content()
    }
}
