package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    dark: Boolean = false,
) {
    val spacing = UnigridTheme.spacing
    val bgColor = if (dark) UgBlack else UgWhite
    val contentColor = if (dark) UgWhite else UgBlack
    val bottomBorderColor = if (dark) UgBlack else UgLightGray

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(bgColor)
            .drawBehind {
                if (!dark) {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = bottomBorderColor,
                        start = Offset(0f, size.height - strokeWidth),
                        end = Offset(size.width, size.height - strokeWidth),
                        strokeWidth = strokeWidth,
                    )
                }
            }
            .padding(horizontal = spacing.level2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navigationIcon?.let {
            it()
            Spacer(Modifier.width(spacing.level2))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = contentColor,
            modifier = Modifier.weight(1f),
        )

        actions?.invoke()
    }
}
