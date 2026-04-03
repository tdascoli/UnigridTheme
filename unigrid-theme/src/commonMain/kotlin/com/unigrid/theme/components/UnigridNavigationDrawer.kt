package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgWhite

@Composable
fun UnigridNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    gesturesEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
                .background(UgWhite)
                .border(width = 1.dp, color = UgLightGray)
                .padding(end = 1.dp),
        ) {
            // Right border via border modifier above
            drawerContent()
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            content()
        }
    }
}

@Composable
fun UnigridNavigationDrawerItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    badge: (@Composable () -> Unit)? = null,
) {
    val bgColor = if (selected) UgBlack else UgWhite
    val textColor = if (selected) UgWhite else UgBlack

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
            Spacer(Modifier.width(12.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = Modifier.weight(1f),
        )
        if (badge != null) {
            Spacer(Modifier.width(8.dp))
            badge()
        }
    }
}
