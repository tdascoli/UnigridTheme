package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class ColumnAlignment { Start, Center, End }

data class TableColumn<T>(
    val header: String,
    val weight: Float = 1f,
    val alignment: ColumnAlignment = ColumnAlignment.Start,
    val cell: @Composable (T) -> Unit,
)

@Composable
fun <T> UnigridTable(
    columns: List<TableColumn<T>>,
    rows: List<T>,
    modifier: Modifier = Modifier,
    striped: Boolean = false,
    bordered: Boolean = false,
    compact: Boolean = false,
    dark: Boolean = false,
) {
    val spacing = UnigridTheme.spacing
    val cellPadding = if (compact) spacing.level1 else spacing.level2
    val bgColor = if (dark) UgDarkGray else UgWhite
    val headerBg = if (dark) UgBlack else UgWarmGray
    val textColor = if (dark) UgWhite else UgBlack
    val borderColor = if (dark) UgMediumGray else UgLightGray
    val stripeBg = if (dark) UgBlack.copy(alpha = 0.3f) else UgWarmGray.copy(alpha = 0.5f)

    val borderMod = if (bordered) Modifier.border(1.dp, borderColor) else Modifier

    LazyColumn(modifier = modifier.fillMaxWidth().then(borderMod)) {
        // Header row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBg),
            ) {
                columns.forEach { col ->
                    val align = when (col.alignment) {
                        ColumnAlignment.Start -> Alignment.CenterStart
                        ColumnAlignment.Center -> Alignment.Center
                        ColumnAlignment.End -> Alignment.CenterEnd
                    }
                    Box(
                        modifier = Modifier
                            .weight(col.weight)
                            .padding(cellPadding),
                        contentAlignment = align,
                    ) {
                        Text(
                            text = col.header,
                            style = MaterialTheme.typography.labelLarge.copy(fontFeatureSettings = "liga, calt, case, tnum, lnum, ss07"),
                            color = textColor,
                        )
                    }
                }
            }
        }

        // Data rows
        itemsIndexed(rows) { index, row ->
            val rowBg = when {
                striped && index % 2 == 1 -> stripeBg
                else -> bgColor
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rowBg),
            ) {
                columns.forEach { col ->
                    val align = when (col.alignment) {
                        ColumnAlignment.Start -> Alignment.CenterStart
                        ColumnAlignment.Center -> Alignment.Center
                        ColumnAlignment.End -> Alignment.CenterEnd
                    }
                    Box(
                        modifier = Modifier
                            .weight(col.weight)
                            .padding(cellPadding),
                        contentAlignment = align,
                    ) {
                        col.cell(row)
                    }
                }
            }
        }
    }
}
