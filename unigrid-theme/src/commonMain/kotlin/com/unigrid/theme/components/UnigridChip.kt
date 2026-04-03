package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite

enum class ChipVariant { Assist, Filter, Input, Suggestion }

private val ChipShape = RoundedCornerShape(0.dp)

@Composable
fun UnigridChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ChipVariant = ChipVariant.Assist,
    selected: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    val bgColor = when {
        !enabled -> UgMediumGray.copy(alpha = 0.12f)
        selected -> UgBlack
        else -> Color.Transparent
    }
    val contentColor = when {
        !enabled -> UgMediumGray
        selected -> UgWhite
        else -> UgBlack
    }
    val borderColor = when {
        !enabled -> UgMediumGray.copy(alpha = 0.38f)
        selected -> UgBlack
        else -> UgMediumGray
    }

    Row(
        modifier = modifier
            .height(36.dp)
            .border(1.dp, borderColor, ChipShape)
            .background(bgColor, ChipShape)
            .clip(ChipShape)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (variant == ChipVariant.Filter && selected) {
            Text(
                text = "\u2713",
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
            )
            Spacer(Modifier.width(4.dp))
        } else {
            leadingIcon?.let {
                it()
                Spacer(Modifier.width(4.dp))
            }
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
        )

        trailingIcon?.let {
            Spacer(Modifier.width(4.dp))
            it()
        }
    }
}
