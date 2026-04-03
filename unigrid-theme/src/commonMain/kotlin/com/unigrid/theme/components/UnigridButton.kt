package com.unigrid.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBlue
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgGreen
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class ButtonVariant { Filled, Outline, Ghost }

enum class ButtonColor(val bg: Color, val fg: Color) {
    Black(UgBlack, UgWhite),
    Red(UgRed, UgWhite),
    Brown(UgBrown, UgWhite),
    Green(UgGreen, UgWhite),
    Blue(UgBlue, UgWhite),
    Light(UgLightGray, UgBlack),
}

enum class ButtonSize { Sm, Md, Lg }

private val SharpShape = RoundedCornerShape(0.dp)

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
    val spacing = UnigridTheme.spacing
    val contentPadding = when (size) {
        ButtonSize.Sm -> PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ButtonSize.Md -> PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ButtonSize.Lg -> PaddingValues(horizontal = 28.dp, vertical = 14.dp)
    }
    val textStyle = when (size) {
        ButtonSize.Sm -> MaterialTheme.typography.labelSmall
        ButtonSize.Md -> MaterialTheme.typography.labelSmall
        ButtonSize.Lg -> MaterialTheme.typography.labelMedium
    }
    val widthMod = if (fullWidth) Modifier.fillMaxWidth() else Modifier
    val combinedMod = modifier.then(widthMod)

    val content: @Composable () -> Unit = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.let {
                it()
                Spacer(Modifier.width(spacing.level1))
            }
            Text(text, style = textStyle)
            trailingIcon?.let {
                Spacer(Modifier.width(spacing.level1))
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
