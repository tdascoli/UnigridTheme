package com.unigrid.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.components.ButtonVariant
import com.unigrid.theme.components.UnigridBadge
import com.unigrid.theme.components.UnigridButton
import com.unigrid.theme.components.UnigridCircularProgress
import com.unigrid.theme.components.UnigridDialog
import com.unigrid.theme.components.UnigridLinearProgress
import com.unigrid.theme.components.UnigridSnackbar
import com.unigrid.theme.components.UnigridTooltip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedbackScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Feedback",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        // Badge
        Text(
            text = "Badge",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridBadge(count = 3) {
                Text(
                    text = "\u2709",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
            UnigridBadge {
                Text(
                    text = "\uD83D\uDD14",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Inbox with count (3) and bell with dot badge",
            style = MaterialTheme.typography.bodySmall,
            color = UgMediumGray,
        )

        Spacer(Modifier.height(32.dp))

        // Progress
        Text(
            text = "Progress",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        Text("Linear determinate (50%)", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(4.dp))
        UnigridLinearProgress(progress = 0.5f, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(16.dp))

        Text("Linear indeterminate", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(4.dp))
        UnigridLinearProgress(modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(16.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column {
                Text("Circular 50%", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
                Spacer(Modifier.height(4.dp))
                UnigridCircularProgress(progress = 0.5f, modifier = Modifier.size(40.dp))
            }
            Column {
                Text("Circular indeterminate", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
                Spacer(Modifier.height(4.dp))
                UnigridCircularProgress(modifier = Modifier.size(40.dp))
            }
        }

        Spacer(Modifier.height(32.dp))

        // Dialog
        Text(
            text = "Dialog",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var showDialog by remember { mutableStateOf(false) }

        UnigridButton(
            text = "Open Dialog",
            onClick = { showDialog = true },
        )

        if (showDialog) {
            UnigridDialog(
                onDismissRequest = { showDialog = false },
                title = "Confirm Action",
                text = "Are you sure you want to proceed with this action? This cannot be undone.",
                confirmButton = {
                    UnigridButton(
                        text = "Confirm",
                        onClick = { showDialog = false },
                    )
                },
                dismissButton = {
                    UnigridButton(
                        text = "Cancel",
                        onClick = { showDialog = false },
                        variant = ButtonVariant.Outline,
                    )
                },
            )
        }

        Spacer(Modifier.height(32.dp))

        // Snackbar
        Text(
            text = "Snackbar",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridSnackbar(
            message = "File has been saved successfully.",
            actionLabel = "Undo",
            onAction = {},
        )

        Spacer(Modifier.height(32.dp))

        // Tooltip
        Text(
            text = "Tooltip",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridTooltip(text = "This button does something important") {
            UnigridButton(
                text = "Hover for Tooltip",
                onClick = {},
                variant = ButtonVariant.Outline,
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}
