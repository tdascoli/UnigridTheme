package com.unigrid.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridList(
    modifier: Modifier = Modifier,
    dividers: Boolean = true,
    content: @Composable UnigridListScope.() -> Unit,
) {
    val scope = UnigridListScope(dividers)
    Column(modifier = modifier) {
        scope.content()
    }
}

class UnigridListScope(val dividers: Boolean)

@Composable
fun UnigridListScope.UnigridListItem(
    headlineText: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    overlineText: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val spacing = UnigridTheme.spacing

    if (dividers) {
        HorizontalDivider(thickness = 1.dp, color = UgLightGray)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.level2, vertical = spacing.level1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent?.let {
            it()
            Spacer(Modifier.width(spacing.level2))
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (overlineText != null) {
                Text(
                    text = overlineText,
                    style = MaterialTheme.typography.labelSmall,
                    color = UgMediumGray,
                )
            }
            Text(
                text = headlineText,
                style = MaterialTheme.typography.bodyLarge,
                color = UgBlack,
            )
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = UgMediumGray,
                )
            }
        }

        trailingContent?.let {
            Spacer(Modifier.width(spacing.level2))
            it()
        }
    }
}

/** Standalone list item (not scoped to UnigridList). */
@Composable
fun UnigridListItem(
    headlineText: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    overlineText: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val spacing = UnigridTheme.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.level2, vertical = spacing.level1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent?.let {
            it()
            Spacer(Modifier.width(spacing.level2))
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (overlineText != null) {
                Text(
                    text = overlineText,
                    style = MaterialTheme.typography.labelSmall,
                    color = UgMediumGray,
                )
            }
            Text(
                text = headlineText,
                style = MaterialTheme.typography.bodyLarge,
                color = UgBlack,
            )
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = UgMediumGray,
                )
            }
        }

        trailingContent?.let {
            Spacer(Modifier.width(spacing.level2))
            it()
        }
    }
}
