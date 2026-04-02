package com.unigrid.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class CardAccent(val color: Color) {
    None(Color.Transparent),
    Red(UgRed),
    Brown(UgBrown),
    Green(UgGreen),
    Blue(UgBlue),
}

enum class CardImageRatio(val value: Float) {
    Square(1f),
    Landscape(16f / 9f),
    DinA(1f / 1.414f),
}

class UnigridCardScope(
    val dark: Boolean,
) {
    val contentColor: Color get() = if (dark) UgWhite else UgBlack
    val subtitleColor: Color get() = if (dark) UgLightGray else UgMediumGray
}

@Composable
fun UnigridCard(
    modifier: Modifier = Modifier,
    accent: CardAccent = CardAccent.None,
    dark: Boolean = false,
    bordered: Boolean = false,
    content: @Composable UnigridCardScope.() -> Unit,
) {
    val shape = RoundedCornerShape(0.dp)
    val bgColor = if (dark) UgDarkGray else UgWhite
    val border = if (bordered) BorderStroke(2.dp, if (dark) UgMediumGray else UgLightGray) else null
    val scope = UnigridCardScope(dark)

    Surface(
        modifier = modifier,
        shape = shape,
        color = bgColor,
        border = border,
    ) {
        Column {
            if (accent != CardAccent.None) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(accent.color)
                        .padding(vertical = 1.5.dp) // 3dp total height
                )
            }
            scope.content()
        }
    }
}

@Composable
fun UnigridCardScope.CardImage(
    ratio: CardImageRatio = CardImageRatio.Landscape,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio.value),
    ) {
        content()
    }
}

@Composable
fun UnigridCardScope.CardBody(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val spacing = UnigridTheme.spacing
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.level3),
        verticalArrangement = Arrangement.spacedBy(spacing.level1),
        content = content,
    )
}

@Composable
fun UnigridCardScope.CardTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = contentColor,
        modifier = modifier,
    )
}

@Composable
fun UnigridCardScope.CardSubtitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = subtitleColor,
        modifier = modifier,
    )
}

@Composable
fun UnigridCardScope.CardFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.level3, vertical = spacing.level2),
    ) {
        content()
    }
}
