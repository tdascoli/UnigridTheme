package com.unigrid.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UnigridTheme

data class AccordionItem(
    val title: String,
    val key: String = title,
    val content: @Composable () -> Unit,
)

@Composable
fun UnigridAccordion(
    items: List<AccordionItem>,
    modifier: Modifier = Modifier,
    allowMultiple: Boolean = false,
) {
    val expandedKeys = remember { mutableStateOf(setOf<String>()) }

    Column(modifier = modifier.fillMaxWidth()) {
        items.forEach { item ->
            val expanded = item.key in expandedKeys.value

            UnigridAccordionSection(
                title = item.title,
                expanded = expanded,
                onToggle = {
                    expandedKeys.value = if (expanded) {
                        expandedKeys.value - item.key
                    } else if (allowMultiple) {
                        expandedKeys.value + item.key
                    } else {
                        setOf(item.key)
                    }
                },
                content = item.content,
            )
        }
    }
}

@Composable
fun UnigridAccordionSection(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val indicator = if (expanded) "\u2212" else "+" // minus or plus

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 1.dp, color = UgLightGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(vertical = spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = UgBlack,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = indicator,
                style = MaterialTheme.typography.titleLarge,
                color = UgMediumGray,
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.level3),
            ) {
                content()
            }
        }
    }
}
