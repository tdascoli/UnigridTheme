package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.components.ContainerWidth
import com.unigrid.theme.components.UnigridContainer
import com.unigrid.theme.components.UnigridGrid
import com.unigrid.theme.components.gridSpan

@Composable
fun LayoutScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Layout",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        // Grid demo
        Text(
            text = "12-Column Grid",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridGrid {
            GridCell("Span 6", UgRed, Modifier.gridSpan(6))
            GridCell("Span 6", UgBlue, Modifier.gridSpan(6))
            GridCell("Span 4", UgBrown, Modifier.gridSpan(4))
            GridCell("Span 4", UgGreen, Modifier.gridSpan(4))
            GridCell("Span 4", UgMediumGray, Modifier.gridSpan(4))
            GridCell("Span 12", UgBlack, Modifier.gridSpan(12))
            GridCell("Span 3", UgRed, Modifier.gridSpan(3))
            GridCell("Span 9", UgBlue, Modifier.gridSpan(9))
        }

        Spacer(Modifier.height(32.dp))

        // Container demo
        Text(
            text = "Container Widths",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        ContainerWidth.entries.forEach { cw ->
            Text(
                text = "ContainerWidth.${cw.name} (max: ${cw.maxWidth})",
                style = MaterialTheme.typography.labelMedium,
                color = UgMediumGray,
            )
            Spacer(Modifier.height(4.dp))
            UnigridContainer(width = cw) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(UgWarmGray),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = cw.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = UgBlack,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun GridCell(label: String, color: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(60.dp)
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = UgWhite,
        )
    }
}
