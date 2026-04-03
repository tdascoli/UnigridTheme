package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite

private val SharpShape = RoundedCornerShape(0.dp)

@Composable
fun UnigridBadge(
    count: Int? = null,
    color: Color = UgRed,
    content: @Composable () -> Unit,
) {
    Box {
        content()

        if (count == null) {
            // Dot-only badge
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, SharpShape)
                    .align(Alignment.TopEnd),
            )
        } else {
            // Numbered badge
            Box(
                modifier = Modifier
                    .defaultMinSize(minWidth = 18.dp, minHeight = 18.dp)
                    .background(color, SharpShape)
                    .padding(horizontal = 4.dp)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = UgWhite,
                )
            }
        }
    }
}
