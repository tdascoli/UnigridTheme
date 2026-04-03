package com.unigrid.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite

private val SnackbarShape = RoundedCornerShape(0.dp)

/** Styled snackbar for use with M3 SnackbarHost. */
@Composable
fun UnigridSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        shape = SnackbarShape,
        containerColor = UgBlack,
        contentColor = UgWhite,
        actionColor = UgRed,
        actionContentColor = UgRed,
        dismissActionContentColor = UgWhite,
    )
}

/** Simpler standalone snackbar without SnackbarData. */
@Composable
fun UnigridSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Snackbar(
        modifier = modifier,
        shape = SnackbarShape,
        containerColor = UgBlack,
        contentColor = UgWhite,
        action = if (actionLabel != null && onAction != null) {
            {
                TextButton(onClick = onAction) {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = UgRed,
                    )
                }
            }
        } else null,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = UgWhite,
        )
    }
}
