package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

@Composable
fun SpacingScreen() {
    val spacing = UnigridTheme.spacing

    val levels: List<Triple<String, Int, Dp>> = listOf(
        Triple("level0", 0, spacing.level0),
        Triple("level1", 1, spacing.level1),
        Triple("level2", 2, spacing.level2),
        Triple("level3", 3, spacing.level3),
        Triple("level4", 4, spacing.level4),
        Triple("level5", 5, spacing.level5),
        Triple("level6", 6, spacing.level6),
        Triple("level7", 7, spacing.level7),
        Triple("level8", 8, spacing.level8),
    )

    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Spacing Scale",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        levels.forEach { (name, level, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Text(
                    text = "$name ($value)",
                    style = MaterialTheme.typography.labelMedium,
                    color = UgMediumGray,
                    modifier = Modifier.width(140.dp),
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(value.coerceAtLeast(2.dp))
                        .background(UgRed),
                )
            }
        }
    }
}
