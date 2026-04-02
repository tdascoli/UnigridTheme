package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

data class TabItem(
    val label: String,
    val key: String = label,
)

enum class TabVariant { Default, Bordered, Pills }

@Composable
fun UnigridTabs(
    tabs: List<TabItem>,
    selectedKey: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: TabVariant = TabVariant.Default,
    vertical: Boolean = false,
) {
    val spacing = UnigridTheme.spacing
    val content: @Composable () -> Unit = {
        tabs.forEach { tab ->
            val selected = tab.key == selectedKey
            TabContent(
                tab = tab,
                selected = selected,
                variant = variant,
                onClick = { onTabSelected(tab.key) },
            )
        }
    }

    if (vertical) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(spacing.level1),
        ) {
            content()
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.level1),
        ) {
            content()
        }
    }
}

@Composable
private fun TabContent(
    tab: TabItem,
    selected: Boolean,
    variant: TabVariant,
    onClick: () -> Unit,
) {
    val spacing = UnigridTheme.spacing
    val shape = when (variant) {
        TabVariant.Pills -> RoundedCornerShape(4.dp)
        else -> RoundedCornerShape(0.dp)
    }

    val bgColor = when {
        selected && variant == TabVariant.Pills -> UgBlack
        selected && variant == TabVariant.Bordered -> UgWhite
        else -> Color.Transparent
    }
    val textColor = when {
        selected && variant == TabVariant.Pills -> UgWhite
        selected -> UgBlack
        else -> UgMediumGray
    }
    val borderMod = when {
        variant == TabVariant.Bordered && selected ->
            Modifier.border(2.dp, UgLightGray, shape)
        variant == TabVariant.Bordered ->
            Modifier.border(2.dp, Color.Transparent, shape)
        else -> Modifier
    }
    val underlineMod = when {
        variant == TabVariant.Default && selected ->
            Modifier.border(width = 2.dp, color = UgBlack, shape = RoundedCornerShape(0.dp))
        else -> Modifier
    }

    Box(
        modifier = Modifier
            .clip(shape)
            .then(borderMod)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .then(underlineMod)
            .padding(horizontal = spacing.level2, vertical = spacing.level1),
    ) {
        Text(
            text = tab.label,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
        )
    }
}
