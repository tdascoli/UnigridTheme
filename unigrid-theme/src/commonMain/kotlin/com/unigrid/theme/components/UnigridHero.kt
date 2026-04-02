package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UnigridTheme

enum class HeroHeight(val fraction: Float) {
    Full(1f),
    Half(0.5f),
    Third(0.33f),
}

enum class ContentPosition {
    Top, Center, Bottom
}

@Composable
fun UnigridHero(
    modifier: Modifier = Modifier,
    height: HeroHeight = HeroHeight.Full,
    heightDp: Dp = 600.dp,
    contentPosition: ContentPosition = ContentPosition.Center,
    overlayColor: Color = UgBlack.copy(alpha = 0.4f),
    background: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val heroHeight = heightDp * height.fraction
    val alignment = when (contentPosition) {
        ContentPosition.Top -> Alignment.TopCenter
        ContentPosition.Center -> Alignment.Center
        ContentPosition.Bottom -> Alignment.BottomCenter
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heroHeight),
    ) {
        // Background content slot
        background?.invoke(this)

        // Gradient overlay
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, overlayColor),
                    )
                )
        )

        // Color overlay
        Box(
            Modifier
                .fillMaxSize()
                .background(overlayColor.copy(alpha = overlayColor.alpha * 0.3f))
        )

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.level4),
            contentAlignment = alignment,
        ) {
            content()
        }
    }
}
