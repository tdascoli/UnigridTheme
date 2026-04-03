package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgLightGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.components.UnigridBottomSheet
import com.unigrid.theme.components.UnigridButton
import com.unigrid.theme.components.UnigridList
import com.unigrid.theme.components.UnigridListItem
import com.unigrid.theme.components.UnigridNavigationDrawerItem
import com.unigrid.theme.components.UnigridNavigationRail
import com.unigrid.theme.components.UnigridNavigationRailItem
import com.unigrid.theme.components.UnigridTopAppBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Navigation",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        // TopAppBar
        Text(
            text = "Top App Bar",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        Text("Light variant", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(4.dp))
        UnigridTopAppBar(
            title = "Page Title",
            navigationIcon = {
                Text("\u2190", style = MaterialTheme.typography.titleLarge, color = UgBlack)
            },
            actions = {
                Text("\u22EE", style = MaterialTheme.typography.titleLarge, color = UgBlack)
            },
        )

        Spacer(Modifier.height(16.dp))

        Text("Dark variant", style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
        Spacer(Modifier.height(4.dp))
        UnigridTopAppBar(
            title = "Dashboard",
            dark = true,
            navigationIcon = {
                Text("\u2630", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            },
            actions = {
                Text("\u2699", style = MaterialTheme.typography.titleLarge, color = UgWhite)
            },
        )

        Spacer(Modifier.height(32.dp))

        // List
        Text(
            text = "List",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridList {
            UnigridListItem(
                headlineText = "Inbox",
                supportingText = "3 new messages",
            )
            UnigridListItem(
                headlineText = "Sent",
                supportingText = "Last sent 2 hours ago",
            )
            UnigridListItem(
                headlineText = "Drafts",
                supportingText = "1 draft saved",
            )
            UnigridListItem(
                headlineText = "Archive",
                supportingText = "142 archived items",
            )
        }

        Spacer(Modifier.height(32.dp))

        // NavigationRail
        Text(
            text = "Navigation Rail",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var railIndex by remember { mutableStateOf(0) }

        Box(modifier = Modifier.height(240.dp)) {
            UnigridNavigationRail {
                UnigridNavigationRailItem(
                    selected = railIndex == 0,
                    onClick = { railIndex = 0 },
                    icon = { Text("\u2302", style = MaterialTheme.typography.titleLarge, color = if (railIndex == 0) UgBlack else UgMediumGray) },
                    label = "Home",
                )
                UnigridNavigationRailItem(
                    selected = railIndex == 1,
                    onClick = { railIndex = 1 },
                    icon = { Text("\u2605", style = MaterialTheme.typography.titleLarge, color = if (railIndex == 1) UgBlack else UgMediumGray) },
                    label = "Favorites",
                )
                UnigridNavigationRailItem(
                    selected = railIndex == 2,
                    onClick = { railIndex = 2 },
                    icon = { Text("\u2699", style = MaterialTheme.typography.titleLarge, color = if (railIndex == 2) UgBlack else UgMediumGray) },
                    label = "Settings",
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // NavigationDrawer items
        Text(
            text = "Navigation Drawer Items",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var drawerIndex by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(UgLightGray)
                .padding(1.dp),
        ) {
            UnigridNavigationDrawerItem(
                label = "Home",
                selected = drawerIndex == 0,
                onClick = { drawerIndex = 0 },
            )
            UnigridNavigationDrawerItem(
                label = "Profile",
                selected = drawerIndex == 1,
                onClick = { drawerIndex = 1 },
            )
            UnigridNavigationDrawerItem(
                label = "Settings",
                selected = drawerIndex == 2,
                onClick = { drawerIndex = 2 },
            )
            UnigridNavigationDrawerItem(
                label = "Help",
                selected = drawerIndex == 3,
                onClick = { drawerIndex = 3 },
            )
        }

        Spacer(Modifier.height(32.dp))

        // BottomSheet
        Text(
            text = "Bottom Sheet",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var showSheet by remember { mutableStateOf(false) }

        UnigridButton(
            text = "Open Bottom Sheet",
            onClick = { showSheet = true },
        )

        if (showSheet) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                UnigridBottomSheet(
                    onDismissRequest = { showSheet = false },
                ) {
                    Column {
                        Text(
                            text = "Bottom Sheet",
                            style = MaterialTheme.typography.headlineMedium,
                            color = UgBlack,
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "This is an example bottom sheet with some content. Tap the scrim to dismiss.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = UgBlack,
                        )
                        Spacer(Modifier.height(16.dp))
                        UnigridButton(
                            text = "Close",
                            onClick = { showSheet = false },
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
