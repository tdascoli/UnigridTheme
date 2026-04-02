package com.unigrid.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UnigridTheme

private const val GRID_COLUMNS = 12

data class GridParentData(
    val span: Int = 1,
    val start: Int = -1,
)

private class GridSpanModifier(private val span: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val data = parentData as? GridParentData ?: GridParentData()
        return data.copy(span = span.coerceIn(1, GRID_COLUMNS))
    }
}

private class GridStartModifier(private val start: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        val data = parentData as? GridParentData ?: GridParentData()
        return data.copy(start = start.coerceIn(0, GRID_COLUMNS - 1))
    }
}

fun Modifier.gridSpan(span: Int): Modifier = this.then(GridSpanModifier(span))
fun Modifier.gridStart(start: Int): Modifier = this.then(GridStartModifier(start))

@Composable
fun UnigridGrid(
    modifier: Modifier = Modifier,
    gap: Dp = UnigridTheme.spacing.level3,
    content: @Composable () -> Unit,
) {
    val gapPx = with(androidx.compose.ui.platform.LocalDensity.current) { gap.roundToPx() }

    Layout(
        content = content,
        modifier = modifier.fillMaxWidth(),
    ) { measurables, constraints ->
        val totalWidth = constraints.maxWidth
        val totalGap = gapPx * (GRID_COLUMNS - 1)
        val colWidth = (totalWidth - totalGap) / GRID_COLUMNS

        // Measure children
        val placeables = measurables.map { measurable ->
            val data = measurable.parentData as? GridParentData ?: GridParentData()
            val span = data.span.coerceIn(1, GRID_COLUMNS)
            val childWidth = colWidth * span + gapPx * (span - 1)
            measurable.measure(
                Constraints(
                    minWidth = childWidth.coerceAtLeast(0),
                    maxWidth = childWidth.coerceAtLeast(0),
                    minHeight = 0,
                    maxHeight = constraints.maxHeight,
                )
            )
        }

        // Place children in rows
        var currentCol = 0
        var currentY = 0
        var rowHeight = 0

        val positions = mutableListOf<Pair<Int, Int>>()
        for (i in placeables.indices) {
            val data = measurables[i].parentData as? GridParentData ?: GridParentData()
            val span = data.span.coerceIn(1, GRID_COLUMNS)
            val startCol = if (data.start >= 0) data.start else currentCol

            // Wrap to next row if needed
            if (startCol + span > GRID_COLUMNS) {
                currentY += rowHeight + gapPx
                rowHeight = 0
                val resolvedStart = if (data.start >= 0) data.start else 0
                positions.add(
                    Pair(resolvedStart * (colWidth + gapPx), currentY)
                )
                currentCol = resolvedStart + span
            } else {
                positions.add(
                    Pair(startCol * (colWidth + gapPx), currentY)
                )
                currentCol = startCol + span
            }
            rowHeight = maxOf(rowHeight, placeables[i].height)
        }

        val totalHeight = currentY + rowHeight
        layout(totalWidth, totalHeight) {
            placeables.forEachIndexed { i, placeable ->
                placeable.placeRelative(positions[i].first, positions[i].second)
            }
        }
    }
}

// Container

enum class ContainerWidth(val maxWidth: Dp) {
    Narrow(1280.dp),
    Wide(1600.dp),
    Fluid(Dp.Infinity),
}

@Composable
fun UnigridContainer(
    modifier: Modifier = Modifier,
    width: ContainerWidth = ContainerWidth.Narrow,
    flush: Boolean = false,
    content: @Composable () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val paddingMod = if (flush) Modifier else Modifier.padding(horizontal = spacing.level4)
    val widthMod = if (width == ContainerWidth.Fluid) {
        Modifier.fillMaxWidth()
    } else {
        Modifier.widthIn(max = width.maxWidth)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = widthMod
                .fillMaxWidth()
                .then(paddingMod),
        ) {
            content()
        }
    }
}
