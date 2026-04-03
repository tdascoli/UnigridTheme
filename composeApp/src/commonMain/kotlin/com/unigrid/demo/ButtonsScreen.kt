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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.components.ButtonColor
import com.unigrid.theme.components.ButtonSize
import com.unigrid.theme.components.ButtonVariant
import com.unigrid.theme.components.UnigridButton
import com.unigrid.theme.components.UnigridButtonGroup
import com.unigrid.theme.components.UnigridButtonGroupItem
import com.unigrid.theme.components.UnigridFab
import com.unigrid.theme.components.UnigridIconButton
import com.unigrid.theme.components.UnigridSplitButton
import com.unigrid.theme.components.UnigridToggleButton

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

        // --- Standard Buttons ---
        ButtonVariant.entries.forEach { variant ->
            Text(
                text = "${variant.name} Variant",
                style = MaterialTheme.typography.headlineMedium,
                color = UgBlack,
            )
            Spacer(Modifier.height(12.dp))

            Text("Colors", style = MaterialTheme.typography.labelLarge, color = UgMediumGray)
            Spacer(Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ButtonColor.entries.forEach { color ->
                    UnigridButton(text = color.name, onClick = {}, variant = variant, color = color)
                }
            }
            Spacer(Modifier.height(16.dp))

            Text("Sizes", style = MaterialTheme.typography.labelLarge, color = UgMediumGray)
            Spacer(Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ButtonSize.entries.forEach { size ->
                    UnigridButton(text = size.name, onClick = {}, variant = variant, size = size)
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        // --- Toggle Buttons ---
        Text(
            text = "Toggle Buttons",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var bold by remember { mutableStateOf(false) }
        var italic by remember { mutableStateOf(false) }
        var underline by remember { mutableStateOf(true) }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridToggleButton(text = "B", selected = bold, onToggle = { bold = !bold })
            UnigridToggleButton(text = "I", selected = italic, onToggle = { italic = !italic })
            UnigridToggleButton(text = "U", selected = underline, onToggle = { underline = !underline })
        }
        Spacer(Modifier.height(24.dp))

        // --- Icon Buttons ---
        Text(
            text = "Icon Buttons",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridIconButton(onClick = {}, variant = ButtonVariant.Filled) {
                Text("+", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            }
            UnigridIconButton(onClick = {}, variant = ButtonVariant.Outline) {
                Text("\u2715", style = MaterialTheme.typography.titleLarge, color = UgBlack)
            }
            UnigridIconButton(onClick = {}, variant = ButtonVariant.Ghost) {
                Text("\u2630", style = MaterialTheme.typography.titleLarge, color = UgBlack)
            }
            UnigridIconButton(onClick = {}, color = ButtonColor.Red) {
                Text("\u2764", style = MaterialTheme.typography.titleMedium, color = UgWhite)
            }
        }
        Spacer(Modifier.height(24.dp))

        // --- Split Button ---
        Text(
            text = "Split Button",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridSplitButton(text = "Save", onMainClick = {}, onDropdownClick = {})
            UnigridSplitButton(
                text = "Action",
                onMainClick = {},
                onDropdownClick = {},
                color = ButtonColor.Red,
            )
        }
        Spacer(Modifier.height(24.dp))

        // --- Button Group ---
        Text(
            text = "Button Group",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var selectedView by remember { mutableStateOf("List") }
        UnigridButtonGroup {
            listOf("List", "Grid", "Table").forEach { label ->
                UnigridButtonGroupItem(
                    text = label,
                    selected = label == selectedView,
                    onClick = { selectedView = label },
                )
            }
        }
        Spacer(Modifier.height(24.dp))

        // --- FAB ---
        Text(
            text = "FAB",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            UnigridFab(onClick = {}) {
                Text("+", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            }
            UnigridFab(onClick = {}, color = ButtonColor.Red) {
                Text("+", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            }
            UnigridFab(onClick = {}, extended = true, text = "Create") {
                Text("+", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}
