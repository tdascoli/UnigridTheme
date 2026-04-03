package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

private val DialogShape = RoundedCornerShape(0.dp)

@Composable
fun UnigridDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String? = null,
    confirmButton: @Composable () -> Unit = {},
    dismissButton: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    val spacing = UnigridTheme.spacing

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = modifier,
            shape = DialogShape,
            color = UgWhite,
            contentColor = UgBlack,
        ) {
            Column(
                modifier = Modifier.padding(spacing.level3),
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = UgBlack,
                    )
                    Spacer(Modifier.height(spacing.level2))
                }

                if (text != null) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgBlack,
                    )
                    Spacer(Modifier.height(spacing.level2))
                }

                if (content != null) {
                    content()
                    Spacer(Modifier.height(spacing.level2))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}
