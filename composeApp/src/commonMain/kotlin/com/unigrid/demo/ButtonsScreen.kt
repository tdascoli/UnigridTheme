package com.unigrid.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.components.ButtonColor
import com.unigrid.theme.components.ButtonSize
import com.unigrid.theme.components.ButtonVariant
import com.unigrid.theme.components.UnigridButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonsScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Buttons",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        ButtonVariant.entries.forEach { variant ->
            Text(
                text = "${variant.name} Variant",
                style = MaterialTheme.typography.headlineMedium,
                color = UgBlack,
            )
            Spacer(Modifier.height(12.dp))

            // All colors at medium size
            Text(
                text = "Colors",
                style = MaterialTheme.typography.labelLarge,
                color = UgMediumGray,
            )
            Spacer(Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ButtonColor.entries.forEach { color ->
                    UnigridButton(
                        text = color.name,
                        onClick = {},
                        variant = variant,
                        color = color,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            // All sizes in black
            Text(
                text = "Sizes",
                style = MaterialTheme.typography.labelLarge,
                color = UgMediumGray,
            )
            Spacer(Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ButtonSize.entries.forEach { size ->
                    UnigridButton(
                        text = size.name,
                        onClick = {},
                        variant = variant,
                        size = size,
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
