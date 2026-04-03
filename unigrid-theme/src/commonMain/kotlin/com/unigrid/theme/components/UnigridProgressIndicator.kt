package com.unigrid.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray

private val SharpShape = RoundedCornerShape(0.dp)

@Composable
fun UnigridLinearProgress(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    color: Color = UgBlack,
    trackColor: Color = UgLightGray,
) {
    val shapedModifier = modifier
        .height(4.dp)
        .clip(SharpShape)

    if (progress != null) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = shapedModifier,
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Square,
        )
    } else {
        LinearProgressIndicator(
            modifier = shapedModifier,
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Square,
        )
    }
}

@Composable
fun UnigridCircularProgress(
    progress: Float? = null,
    modifier: Modifier = Modifier,
    color: Color = UgBlack,
    strokeWidth: Dp = 3.dp,
) {
    if (progress != null) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = modifier,
            color = color,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Square,
        )
    } else {
        CircularProgressIndicator(
            modifier = modifier,
            color = color,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Square,
        )
    }
}
