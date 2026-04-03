package com.unigrid.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWarmGray
import com.unigrid.theme.UgWhite

enum class TextFieldVariant { Filled, Outlined }

// --- Shared decoration content (Column: label on top, input row below) ---

@Composable
private fun TextFieldContent(
    label: String?,
    labelColor: androidx.compose.ui.graphics.Color,
    hasText: Boolean,
    placeholder: String,
    prefix: String?,
    suffix: String?,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    innerTextField: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Label — always on top when present
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = labelColor,
            )
            Spacer(Modifier.height(4.dp))
        }

        // Input row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(Modifier.width(8.dp))
            }
            if (prefix != null) {
                Text(prefix, style = MaterialTheme.typography.bodyMedium, color = UgMediumGray)
                Spacer(Modifier.width(4.dp))
            }
            Box(modifier = Modifier.weight(1f)) {
                if (!hasText && placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgMediumGray,
                    )
                }
                innerTextField()
            }
            if (suffix != null) {
                Spacer(Modifier.width(4.dp))
                Text(suffix, style = MaterialTheme.typography.bodyMedium, color = UgMediumGray)
            }
            if (trailingIcon != null) {
                Spacer(Modifier.width(8.dp))
                trailingIcon()
            }
        }
    }
}

// --- Filled Text Field ---

@Composable
fun UnigridFilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLength: Int? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: String? = null,
    suffix: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val indicatorColor by animateColorAsState(
        targetValue = when {
            isError -> UgRed
            isFocused -> UgBlack
            else -> UgMediumGray
        },
        animationSpec = tween(150),
    )
    val indicatorWidth by animateDpAsState(
        targetValue = if (isFocused || isError) 2.dp else 1.dp,
        animationSpec = tween(150),
    )
    val containerColor = if (enabled) UgWarmGray else UgLightGray.copy(alpha = 0.5f)
    val textColor = if (enabled) UgBlack else UgMediumGray
    val labelColor = when {
        isError -> UgRed
        isFocused -> UgBlack
        else -> UgMediumGray
    }

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            enabled = enabled,
            singleLine = singleLine,
            minLines = if (singleLine) 1 else minLines,
            textStyle = MaterialTheme.typography.bodyMedium.merge(TextStyle(color = textColor)),
            cursorBrush = SolidColor(if (isError) UgRed else UgBlack),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                val indColor = indicatorColor
                val indWidth = indicatorWidth
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .background(containerColor, RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                        .drawBehind {
                            drawLine(
                                color = indColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = indWidth.toPx(),
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    TextFieldContent(
                        label = label,
                        labelColor = labelColor,
                        hasText = value.isNotEmpty(),
                        placeholder = placeholder,
                        prefix = prefix,
                        suffix = suffix,
                        leadingIcon = leadingIcon,
                        trailingIcon = trailingIcon,
                        innerTextField = innerTextField,
                    )
                }
            },
        )

        SupportingTextRow(supportingText, isError, maxLength, value.length)
    }
}

// --- Outlined Text Field ---

@Composable
fun UnigridOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLength: Int? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: String? = null,
    suffix: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> UgLightGray
            isError -> UgRed
            isFocused -> UgBlack
            else -> UgMediumGray
        },
        animationSpec = tween(150),
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused || isError) 2.dp else 1.dp,
        animationSpec = tween(150),
    )
    val textColor = if (enabled) UgBlack else UgMediumGray
    val labelColor = when {
        isError -> UgRed
        isFocused -> UgBlack
        else -> UgMediumGray
    }

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            enabled = enabled,
            singleLine = singleLine,
            minLines = if (singleLine) 1 else minLines,
            textStyle = MaterialTheme.typography.bodyMedium.merge(TextStyle(color = textColor)),
            cursorBrush = SolidColor(if (isError) UgRed else UgBlack),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .border(borderWidth, borderColor, RoundedCornerShape(2.dp))
                        .background(
                            if (enabled) UgWhite else UgLightGray.copy(alpha = 0.2f),
                            RoundedCornerShape(2.dp),
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    TextFieldContent(
                        label = label,
                        labelColor = labelColor,
                        hasText = value.isNotEmpty(),
                        placeholder = placeholder,
                        prefix = prefix,
                        suffix = suffix,
                        leadingIcon = leadingIcon,
                        trailingIcon = trailingIcon,
                        innerTextField = innerTextField,
                    )
                }
            },
        )

        SupportingTextRow(supportingText, isError, maxLength, value.length)
    }
}

// --- Supporting text (shared) ---

@Composable
private fun SupportingTextRow(
    supportingText: String?,
    isError: Boolean,
    maxLength: Int?,
    currentLength: Int,
) {
    if (supportingText != null || maxLength != null) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isError) UgRed else UgMediumGray,
                    modifier = Modifier.weight(1f),
                )
            } else {
                Spacer(Modifier.weight(1f))
            }
            if (maxLength != null) {
                Text(
                    text = "$currentLength/$maxLength",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isError) UgRed else UgMediumGray,
                )
            }
        }
    }
}

// --- Convenience: Unified API ---

@Composable
fun UnigridTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: TextFieldVariant = TextFieldVariant.Outlined,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLength: Int? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: String? = null,
    suffix: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    when (variant) {
        TextFieldVariant.Filled -> UnigridFilledTextField(
            value, onValueChange, modifier, label, placeholder, supportingText,
            isError, enabled, singleLine, minLines, maxLength, leadingIcon,
            trailingIcon, prefix, suffix, keyboardOptions, keyboardActions,
            visualTransformation,
        )
        TextFieldVariant.Outlined -> UnigridOutlinedTextField(
            value, onValueChange, modifier, label, placeholder, supportingText,
            isError, enabled, singleLine, minLines, maxLength, leadingIcon,
            trailingIcon, prefix, suffix, keyboardOptions, keyboardActions,
            visualTransformation,
        )
    }
}
