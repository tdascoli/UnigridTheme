package com.unigrid.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val UnigridLogoVector: ImageVector
    get() = ImageVector.Builder(
        name = "UnigridLogo",
        defaultWidth = 105.833f.dp,
        defaultHeight = 105.833f.dp,
        viewportWidth = 105.833f,
        viewportHeight = 105.833f,
    ).apply {
        // Black background
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(0f, 0f)
            horizontalLineTo(105.833f)
            verticalLineTo(105.833f)
            horizontalLineTo(0f)
            close()
        }
        // White "U" letterform
        path(fill = SolidColor(Color(0xFFFFFFFF))) {
            moveTo(68.792f, 75.59f)
            quadTo(60.79f, 75.59f, 54.852f, 72.664f)
            quadTo(48.916f, 69.738f, 45.603f, 64.49f)
            quadTo(42.333f, 59.198f, 42.333f, 52.185f)
            verticalLineTo(10.583f)
            horizontalLineTo(55.455f)
            verticalLineTo(51.11f)
            quadTo(55.455f, 56.703f, 59.069f, 60.36f)
            quadTo(62.726f, 63.973f, 68.792f, 63.973f)
            quadTo(74.815f, 63.973f, 78.429f, 60.359f)
            quadTo(82.085f, 56.703f, 82.085f, 51.11f)
            verticalLineTo(10.584f)
            horizontalLineTo(95.25f)
            verticalLineTo(52.186f)
            quadTo(95.25f, 59.199f, 91.894f, 64.491f)
            quadTo(88.582f, 69.739f, 82.644f, 72.665f)
            quadTo(76.708f, 75.59f, 68.792f, 75.59f)
            close()
        }
    }.build()

@Composable
fun UnigridLogo(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    contentDescription: String = "Unigrid",
) {
    Image(
        imageVector = UnigridLogoVector,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
    )
}
