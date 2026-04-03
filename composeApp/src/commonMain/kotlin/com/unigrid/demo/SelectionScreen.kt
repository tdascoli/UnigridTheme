package com.unigrid.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.unigrid.theme.components.ChipVariant
import com.unigrid.theme.components.UnigridButton
import com.unigrid.theme.components.UnigridChip
import com.unigrid.theme.components.UnigridMenu
import com.unigrid.theme.components.UnigridMenuItem
import com.unigrid.theme.components.UnigridSearchBar
import com.unigrid.theme.components.UnigridSlider

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Selection",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        // Chips
        Text(
            text = "Chips",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        Text("Filter Chips", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(8.dp))

        var filterA by remember { mutableStateOf(true) }
        var filterB by remember { mutableStateOf(false) }
        var filterC by remember { mutableStateOf(true) }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridChip(
                text = "Kotlin",
                onClick = { filterA = !filterA },
                variant = ChipVariant.Filter,
                selected = filterA,
            )
            UnigridChip(
                text = "Swift",
                onClick = { filterB = !filterB },
                variant = ChipVariant.Filter,
                selected = filterB,
            )
            UnigridChip(
                text = "Compose",
                onClick = { filterC = !filterC },
                variant = ChipVariant.Filter,
                selected = filterC,
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("Assist Chips", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridChip(text = "Share", onClick = {}, variant = ChipVariant.Assist)
            UnigridChip(text = "Bookmark", onClick = {}, variant = ChipVariant.Assist)
            UnigridChip(text = "Export", onClick = {}, variant = ChipVariant.Assist)
        }

        Spacer(Modifier.height(16.dp))

        Text("Input Chips", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UnigridChip(text = "Alice", onClick = {}, variant = ChipVariant.Input, selected = true)
            UnigridChip(text = "Bob", onClick = {}, variant = ChipVariant.Input)
            UnigridChip(text = "Charlie", onClick = {}, variant = ChipVariant.Input)
        }

        Spacer(Modifier.height(32.dp))

        // Slider
        Text(
            text = "Slider",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var sliderValue by remember { mutableStateOf(0.4f) }
        Text(
            text = "Value: ${(sliderValue * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(4.dp))
        UnigridSlider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(32.dp))

        // Menu
        Text(
            text = "Menu",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var menuExpanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("None") }

        Box {
            UnigridButton(
                text = "Open Menu",
                onClick = { menuExpanded = true },
            )
            UnigridMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                UnigridMenuItem(
                    text = "Edit",
                    onClick = { selectedItem = "Edit"; menuExpanded = false },
                )
                UnigridMenuItem(
                    text = "Duplicate",
                    onClick = { selectedItem = "Duplicate"; menuExpanded = false },
                )
                UnigridMenuItem(
                    text = "Archive",
                    onClick = { selectedItem = "Archive"; menuExpanded = false },
                )
                UnigridMenuItem(
                    text = "Delete",
                    onClick = { selectedItem = "Delete"; menuExpanded = false },
                    trailingText = "Ctrl+D",
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Selected: $selectedItem",
            style = MaterialTheme.typography.bodySmall,
            color = UgMediumGray,
        )

        Spacer(Modifier.height(32.dp))

        // Search Bar
        Text(
            text = "Search Bar",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var searchQuery by remember { mutableStateOf("") }

        UnigridSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {},
            placeholder = "Search components...",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))
    }
}
