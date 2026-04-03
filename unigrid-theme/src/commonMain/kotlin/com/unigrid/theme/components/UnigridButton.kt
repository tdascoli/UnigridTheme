package com.unigrid.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite

enum class ButtonVariant { Filled, Outline, Ghost }

enum class ButtonColor(val bg: Color, val fg: Color) {
    Black(UgBlack, UgWhite),
    Red(UgRed, UgWhite),
    Brown(UgBrown, UgWhite),
    Green(UgGreen, UgWhite),
    Blue(UgBlue, UgWhite),
    Light(UgLightGray, UgBlack),
}

enum class ButtonSize { Md, Lg }

private val SharpShape = RoundedCornerShape(0.dp)

// --- Standard Button ---

@Composable
fun UnigridButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled,
    color: ButtonColor = ButtonColor.Black,
    size: ButtonSize = ButtonSize.Md,
    fullWidth: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val contentPadding = when (size) {
        ButtonSize.Md -> PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ButtonSize.Lg -> PaddingValues(horizontal = 28.dp, vertical = 14.dp)
    }
    val textStyle = when (size) {
        ButtonSize.Md -> MaterialTheme.typography.labelSmall
        ButtonSize.Lg -> MaterialTheme.typography.labelMedium
    }
    val widthMod = if (fullWidth) Modifier.fillMaxWidth() else Modifier
    val combinedMod = modifier.then(widthMod)

    val content: @Composable () -> Unit = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.let {
                it()
                Spacer(Modifier.width(8.dp))
            }
            Text(text, style = textStyle)
            trailingIcon?.let {
                Spacer(Modifier.width(8.dp))
                it()
            }
        }
    }

    when (variant) {
        ButtonVariant.Filled -> {
            Button(
                onClick = onClick,
                modifier = combinedMod,
                enabled = enabled,
                shape = SharpShape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.buttonColors(
                    containerColor = color.bg,
                    contentColor = color.fg,
                    disabledContainerColor = UgMediumGray,
                    disabledContentColor = UgWhite,
                ),
                content = { content() },
            )
        }
        ButtonVariant.Outline -> {
            OutlinedButton(
                onClick = onClick,
                modifier = combinedMod,
                enabled = enabled,
                shape = SharpShape,
                contentPadding = contentPadding,
                border = BorderStroke(2.dp, if (enabled) color.bg else UgMediumGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = color.bg,
                    disabledContentColor = UgMediumGray,
                ),
                content = { content() },
            )
        }
        ButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                modifier = combinedMod,
                enabled = enabled,
                shape = SharpShape,
                contentPadding = contentPadding,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = color.bg,
                    disabledContentColor = UgMediumGray,
                ),
                content = { content() },
            )
        }
    }
}

// --- Toggle Button ---

@Composable
fun UnigridToggleButton(
    text: String,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Black,
    enabled: Boolean = true,
) {
    val bgColor = if (selected) color.bg else Color.Transparent
    val fgColor = if (selected) color.fg else color.bg
    val borderColor = if (enabled) color.bg else UgMediumGray

    Box(
        modifier = modifier
            .border(2.dp, borderColor, SharpShape)
            .background(if (enabled) bgColor else UgMediumGray.copy(alpha = 0.2f), SharpShape)
            .clip(SharpShape)
            .clickable(enabled = enabled, onClick = onToggle)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = if (enabled) fgColor else UgMediumGray,
        )
    }
}

// --- Icon Button ---

@Composable
fun UnigridIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Black,
    variant: ButtonVariant = ButtonVariant.Filled,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val bgColor = when (variant) {
        ButtonVariant.Filled -> if (enabled) color.bg else UgMediumGray
        else -> Color.Transparent
    }
    val fgColor = when (variant) {
        ButtonVariant.Filled -> color.fg
        else -> if (enabled) color.bg else UgMediumGray
    }
    val borderMod = when (variant) {
        ButtonVariant.Outline -> Modifier.border(2.dp, if (enabled) color.bg else UgMediumGray, SharpShape)
        else -> Modifier
    }

    Box(
        modifier = modifier
            .size(44.dp)
            .then(borderMod)
            .background(bgColor, SharpShape)
            .clip(SharpShape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

// --- Split Button ---

@Composable
fun UnigridSplitButton(
    text: String,
    onMainClick: () -> Unit,
    onDropdownClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Black,
    enabled: Boolean = true,
) {
    Row(modifier = modifier) {
        Button(
            onClick = onMainClick,
            enabled = enabled,
            shape = SharpShape,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color.bg,
                contentColor = color.fg,
                disabledContainerColor = UgMediumGray,
                disabledContentColor = UgWhite,
            ),
        ) {
            Text(text, style = MaterialTheme.typography.labelSmall)
        }
        Box(
            modifier = Modifier
                .background(if (enabled) color.bg else UgMediumGray)
                .padding(start = 1.dp)
        ) {
            Button(
                onClick = onDropdownClick,
                enabled = enabled,
                shape = SharpShape,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (enabled) color.bg.darken() else UgMediumGray,
                    contentColor = color.fg,
                    disabledContainerColor = UgMediumGray,
                    disabledContentColor = UgWhite,
                ),
            ) {
                Text("\u25BE", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

// --- Button Group ---

@Composable
fun UnigridButtonGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        content()
    }
}

@Composable
fun UnigridButtonGroupItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Black,
) {
    val bgColor = if (selected) color.bg else Color.Transparent
    val fgColor = if (selected) color.fg else color.bg

    Box(
        modifier = modifier
            .border(2.dp, color.bg, SharpShape)
            .background(bgColor, SharpShape)
            .clip(SharpShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = fgColor,
        )
    }
}

// --- FAB ---

@Composable
fun UnigridFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ButtonColor = ButtonColor.Black,
    extended: Boolean = false,
    text: String? = null,
    icon: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color.bg, SharpShape)
            .clip(SharpShape)
            .clickable(onClick = onClick)
            .padding(
                horizontal = if (extended) 20.dp else 16.dp,
                vertical = 16.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (extended && text != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon()
                Spacer(Modifier.width(12.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = color.fg,
                )
            }
        } else {
            icon()
        }
    }
}

// Helper to darken a color slightly for the split button dropdown
private fun Color.darken(): Color {
    return Color(
        red = (red * 0.85f).coerceIn(0f, 1f),
        green = (green * 0.85f).coerceIn(0f, 1f),
        blue = (blue * 0.85f).coerceIn(0f, 1f),
        alpha = alpha,
    )
}
