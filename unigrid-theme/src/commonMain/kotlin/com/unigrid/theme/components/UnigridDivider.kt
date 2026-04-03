package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgLightGray

@Composable
fun UnigridDivider(
    modifier: Modifier = Modifier,
    vertical: Boolean = false,
    thick: Boolean = false,
    color: Color = UgLightGray,
) {
    val thickness = if (thick) 2.dp else 1.dp

    if (vertical) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .width(thickness)
                .background(color),
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(thickness)
                .background(color),
        )
    }
}
