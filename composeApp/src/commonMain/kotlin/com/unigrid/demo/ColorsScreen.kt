package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UgWhite

private data class ColorSwatch(val name: String, val color: Color, val hex: String)

private val swatches = listOf(
    ColorSwatch("UgBlack", UgBlack, "#1A1A1A"),
    ColorSwatch("UgWhite", UgWhite, "#FFFFFF"),
    ColorSwatch("UgWarmGray", UgWarmGray, "#F5F2ED"),
    ColorSwatch("UgLightGray", UgLightGray, "#E8E5E0"),
    ColorSwatch("UgMediumGray", UgMediumGray, "#666666"),
    ColorSwatch("UgDarkGray", UgDarkGray, "#333333"),
    ColorSwatch("UgRed", UgRed, "#C1272D"),
    ColorSwatch("UgBrown", UgBrown, "#4A3728"),
    ColorSwatch("UgGreen", UgGreen, "#2D5A27"),
    ColorSwatch("UgBlue", UgBlue, "#274A5A"),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorsScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Color Palette",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            swatches.forEach { swatch ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(swatch.color)
                            .border(1.dp, UgLightGray),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = swatch.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = UgBlack,
                    )
                    Text(
                        text = swatch.hex,
                        style = MaterialTheme.typography.bodySmall,
                        color = UgMediumGray,
                    )
                }
            }
        }
    }
}
