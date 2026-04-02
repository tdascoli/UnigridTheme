package com.unigrid.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class InputSize(val height: Dp) {
    Sm(36.dp),
    Md(44.dp),
    Lg(52.dp),
}

@Composable
fun UnigridFormGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level1),
    ) {
        content()
    }
}

@Composable
fun UnigridLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = UgBlack,
        modifier = modifier,
    )
}

@Composable
fun UnigridInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    size: InputSize = InputSize.Md,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    val borderColor = when {
        isError -> UgRed
        !enabled -> UgLightGray
        else -> UgMediumGray
    }
    val textColor = if (enabled) UgBlack else UgMediumGray

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.merge(TextStyle(color = textColor)),
        cursorBrush = SolidColor(UgBlack),
        modifier = modifier
            .fillMaxWidth()
            .height(size.height)
            .border(1.dp, borderColor, RoundedCornerShape(2.dp))
            .background(if (enabled) UgWhite else UgLightGray.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
            .padding(horizontal = 12.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgMediumGray,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
fun UnigridTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    minLines: Int = 4,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    val borderColor = when {
        isError -> UgRed
        !enabled -> UgLightGray
        else -> UgMediumGray
    }
    val textColor = if (enabled) UgBlack else UgMediumGray

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyMedium.merge(TextStyle(color = textColor)),
        cursorBrush = SolidColor(UgBlack),
        minLines = minLines,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(2.dp))
            .background(if (enabled) UgWhite else UgLightGray.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
            .padding(12.dp),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgMediumGray,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
fun UnigridCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.clickable(enabled = enabled) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = if (checked) 0.dp else 2.dp,
                    color = if (enabled) UgBlack else UgMediumGray,
                    shape = RoundedCornerShape(2.dp),
                )
                .background(
                    color = if (checked) {
                        if (enabled) UgBlack else UgMediumGray
                    } else UgWhite,
                    shape = RoundedCornerShape(2.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Text(
                    text = "\u2713",
                    color = UgWhite,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        if (label != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) UgBlack else UgMediumGray,
            )
        }
    }
}

@Composable
fun UnigridRadio(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.clickable(enabled = enabled, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(2.dp, if (enabled) UgBlack else UgMediumGray, CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            if (enabled) UgBlack else UgMediumGray,
                            CircleShape,
                        ),
                )
            }
        }
        if (label != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) UgBlack else UgMediumGray,
            )
        }
    }
}

@Composable
fun UnigridSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.clickable(enabled = enabled) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val trackWidth = 44.dp
        val trackHeight = 24.dp
        val thumbSize = 18.dp
        val trackColor = if (checked) {
            if (enabled) UgBlack else UgMediumGray
        } else {
            if (enabled) UgLightGray else UgLightGray.copy(alpha = 0.5f)
        }
        val thumbColor = UgWhite

        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(trackHeight)
                .background(trackColor, CircleShape),
            contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .size(thumbSize)
                    .background(thumbColor, CircleShape),
            )
        }
        if (label != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) UgBlack else UgMediumGray,
            )
        }
    }
}

@Composable
fun UnigridHelpText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = UgMediumGray,
        modifier = modifier,
    )
}

@Composable
fun UnigridErrorText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = UgRed,
        modifier = modifier,
    )
}
