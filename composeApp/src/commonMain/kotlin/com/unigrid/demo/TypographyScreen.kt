package com.unigrid.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray

@Composable
fun TypographyScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Typography",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        val styles: List<Pair<String, TextStyle>> = listOf(
            "displayLarge" to MaterialTheme.typography.displayLarge,
            "displayMedium" to MaterialTheme.typography.displayMedium,
            "displaySmall" to MaterialTheme.typography.displaySmall,
            "headlineLarge" to MaterialTheme.typography.headlineLarge,
            "headlineMedium" to MaterialTheme.typography.headlineMedium,
            "headlineSmall" to MaterialTheme.typography.headlineSmall,
            "titleLarge" to MaterialTheme.typography.titleLarge,
            "titleMedium" to MaterialTheme.typography.titleMedium,
            "titleSmall" to MaterialTheme.typography.titleSmall,
            "bodyLarge" to MaterialTheme.typography.bodyLarge,
            "bodyMedium" to MaterialTheme.typography.bodyMedium,
            "bodySmall" to MaterialTheme.typography.bodySmall,
            "labelLarge" to MaterialTheme.typography.labelLarge,
            "labelMedium" to MaterialTheme.typography.labelMedium,
            "labelSmall" to MaterialTheme.typography.labelSmall,
        )

        styles.forEach { (name, style) ->
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                color = UgMediumGray,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "The quick brown fox jumps over the lazy dog",
                style = style,
                color = UgBlack,
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = UgLightGray)
            Spacer(Modifier.height(12.dp))
        }
    }
}
