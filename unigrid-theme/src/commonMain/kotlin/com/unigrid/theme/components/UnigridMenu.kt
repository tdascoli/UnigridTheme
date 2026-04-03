package com.unigrid.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite

private val MenuShape = RoundedCornerShape(0.dp)

@Composable
fun UnigridMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.border(1.dp, UgLightGray, MenuShape),
        shape = MenuShape,
        containerColor = UgWhite,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
    ) {
        content()
    }
}

@Composable
fun UnigridMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingText: String? = null,
    enabled: Boolean = true,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        onClick = onClick,
        modifier = modifier.height(44.dp),
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = if (trailingText != null) {
            {
                Text(
                    text = trailingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = UgMediumGray,
                )
            }
        } else null,
        colors = MenuDefaults.itemColors(
            textColor = UgBlack,
            disabledTextColor = UgMediumGray,
            leadingIconColor = UgBlack,
            disabledLeadingIconColor = UgMediumGray,
        ),
        contentPadding = MenuDefaults.DropdownMenuItemContentPadding,
    )
}
