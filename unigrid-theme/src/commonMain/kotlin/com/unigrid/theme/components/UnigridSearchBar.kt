package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

@Composable
fun UnigridSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    leadingIcon: (@Composable () -> Unit)? = null,
    active: Boolean = false,
) {
    val shape = RoundedCornerShape(0.dp)

    Column(modifier = modifier) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.merge(TextStyle(color = UgBlack)),
            cursorBrush = SolidColor(UgBlack),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(1.dp, UgMediumGray, shape)
                .background(UgWhite, shape)
                .padding(horizontal = 12.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    } else {
                        Text(
                            text = "\u2315",
                            style = MaterialTheme.typography.bodyLarge,
                            color = UgMediumGray,
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (query.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = UgMediumGray,
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )

        if (active) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, UgMediumGray, shape)
                    .background(UgWhite, shape)
                    .padding(UnigridTheme.spacing.level2),
            ) {
                Text(
                    text = "Type to search\u2026",
                    style = MaterialTheme.typography.bodySmall,
                    color = UgMediumGray,
                )
            }
        }
    }
}
