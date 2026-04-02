package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class SectionColor(val bg: Color, val fg: Color) {
    Black(UgBlack, UgWhite),
    Brown(UgBrown, UgWhite),
    Red(UgRed, UgWhite),
    Green(UgGreen, UgWhite),
    Blue(UgBlue, UgWhite),
    WarmGray(UgWarmGray, UgBlack),
}

enum class SectionSize { Compact, Default, Large }

@Composable
fun UnigridSection(
    modifier: Modifier = Modifier,
    color: SectionColor = SectionColor.Black,
    size: SectionSize = SectionSize.Default,
    title: String? = null,
    subtitle: String? = null,
    content: @Composable () -> Unit = {},
) {
    val spacing = UnigridTheme.spacing
    val vPadding = when (size) {
        SectionSize.Compact -> spacing.level3
        SectionSize.Default -> spacing.level6
        SectionSize.Large -> spacing.level8
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color.bg)
            .padding(horizontal = spacing.level4, vertical = vPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.level2),
    ) {
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.displayMedium,
                color = color.fg,
            )
        }
        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = color.fg.copy(alpha = 0.7f),
            )
        }
        content()
    }
}
