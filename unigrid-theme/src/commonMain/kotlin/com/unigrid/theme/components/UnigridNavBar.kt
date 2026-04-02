package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridNavBar(
    modifier: Modifier = Modifier,
    dark: Boolean = true,
    brand: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val bgColor = if (dark) UgBlack else UgWhite
    val borderColor = if (dark) UgDarkGray else UgLightGray

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(horizontal = spacing.level4, vertical = spacing.level2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        brand?.let {
            it()
            Spacer(Modifier.width(spacing.level4))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.level1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

@Composable
fun UnigridNavItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    dark: Boolean = true,
) {
    val spacing = UnigridTheme.spacing
    val textColor = when {
        selected && dark -> UgWhite
        selected && !dark -> UgBlack
        dark -> UgMediumGray
        else -> UgMediumGray
    }

    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = textColor,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = spacing.level2, vertical = spacing.level1),
    )
}
