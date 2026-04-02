package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridPagination(
    currentPage: Int,
    totalPages: Int,
    onPageSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = UnigridTheme.spacing
    val shape = RoundedCornerShape(0.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing.level1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Previous arrow
        PaginationItem(
            text = "\u2190",
            selected = false,
            enabled = currentPage > 1,
            onClick = { if (currentPage > 1) onPageSelected(currentPage - 1) },
        )

        // Page numbers
        for (page in 1..totalPages) {
            PaginationItem(
                text = page.toString(),
                selected = page == currentPage,
                enabled = true,
                onClick = { onPageSelected(page) },
            )
        }

        // Next arrow
        PaginationItem(
            text = "\u2192",
            selected = false,
            enabled = currentPage < totalPages,
            onClick = { if (currentPage < totalPages) onPageSelected(currentPage + 1) },
        )
    }
}

@Composable
private fun PaginationItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(0.dp)
    val bgColor = when {
        selected -> UgBlack
        else -> UgLightGray
    }
    val textColor = when {
        selected -> UgWhite
        !enabled -> UgMediumGray.copy(alpha = 0.5f)
        else -> UgBlack
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(shape)
            .background(bgColor, shape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
        )
    }
}
