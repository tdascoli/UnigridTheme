package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray

private val SharpShape = RoundedCornerShape(0.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnigridSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        valueRange = valueRange,
        steps = steps,
        enabled = enabled,
        colors = SliderDefaults.colors(
            thumbColor = UgBlack,
            activeTrackColor = UgBlack,
            inactiveTrackColor = UgLightGray,
            disabledThumbColor = UgMediumGray,
            disabledActiveTrackColor = UgMediumGray,
            disabledInactiveTrackColor = UgLightGray,
        ),
        thumb = {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = if (enabled) UgBlack else UgMediumGray,
                        shape = SharpShape,
                    ),
            )
        },
    )
}
