package com.unigrid.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme
import com.unigrid.theme.components.UnigridLogo
import com.unigrid.theme.components.UnigridNavBar

enum class DemoScreen(val label: String) {
    Home("Home"),
    Colors("Colors"),
    Typography("Typography"),
    Spacing("Spacing"),
    Buttons("Buttons"),
    Cards("Cards"),
    Layout("Layout"),
    Components("Components"),
}

@Composable
fun App() {
    UnigridTheme(darkTheme = false) {
        var currentScreen by remember { mutableStateOf(DemoScreen.Home) }

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val isCompact = maxWidth < 720.dp

            if (isCompact) {
                CompactLayout(currentScreen) { currentScreen = it }
            } else {
                ExpandedLayout(currentScreen) { currentScreen = it }
            }
        }
    }
}

@Composable
private fun CompactLayout(
    currentScreen: DemoScreen,
    onScreenSelected: (DemoScreen) -> Unit,
) {
    var drawerOpen by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        // Main content
        Column(Modifier.fillMaxSize()) {
            // Top NavBar with hamburger + brand
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UgBlack)
                    .windowInsetsPadding(WindowInsets.statusBars),
            ) {
                UnigridNavBar(
                    dark = true,
                    brand = {
                        // Hamburger button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { drawerOpen = !drawerOpen },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = if (drawerOpen) "\u2715" else "\u2630",
                                color = UgWhite,
                                fontSize = 22.sp,
                            )
                        }
                        UnigridLogo(size = 32.dp)
                    },
                ) {}
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .windowInsetsPadding(WindowInsets.navigationBars),
            ) {
                ScreenContent(currentScreen)
            }
        }

        // Drawer overlay — scrim
        if (drawerOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(UgBlack.copy(alpha = 0.5f))
                    .clickable { drawerOpen = false },
            )
        }

        // Slide-in drawer
        AnimatedVisibility(
            visible = drawerOpen,
            enter = slideInHorizontally { -it },
            exit = slideOutHorizontally { -it },
        ) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .background(UgBlack)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(vertical = 24.dp),
            ) {
                UnigridLogo(
                    size = 48.dp,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Theme Demo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = UgMediumGray,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
                Spacer(Modifier.height(32.dp))

                DemoScreen.entries.forEach { screen ->
                    val selected = screen == currentScreen
                    val bgColor = if (selected) UgDarkGray else UgBlack
                    val textColor = if (selected) UgRed else UgWhite

                    Text(
                        text = screen.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onScreenSelected(screen)
                                drawerOpen = false
                            }
                            .background(bgColor)
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandedLayout(
    currentScreen: DemoScreen,
    onScreenSelected: (DemoScreen) -> Unit,
) {
    Row(Modifier.fillMaxSize()) {
        // Sidebar
        Column(
            modifier = Modifier
                .width(220.dp)
                .fillMaxHeight()
                .background(UgBlack)
                .padding(vertical = 24.dp),
        ) {
            UnigridLogo(
                size = 56.dp,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Theme Demo",
                style = MaterialTheme.typography.bodyMedium,
                color = UgMediumGray,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(Modifier.height(32.dp))

            DemoScreen.entries.forEach { screen ->
                val selected = screen == currentScreen
                val bgColor = if (selected) UgDarkGray else UgBlack
                val textColor = if (selected) UgRed else UgMediumGray

                Text(
                    text = screen.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onScreenSelected(screen) }
                        .background(bgColor)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            ScreenContent(currentScreen)
        }
    }
}

@Composable
private fun ScreenContent(currentScreen: DemoScreen) {
    when (currentScreen) {
        DemoScreen.Home -> HomeScreen()
        DemoScreen.Colors -> ColorsScreen()
        DemoScreen.Typography -> TypographyScreen()
        DemoScreen.Spacing -> SpacingScreen()
        DemoScreen.Buttons -> ButtonsScreen()
        DemoScreen.Cards -> CardsScreen()
        DemoScreen.Layout -> LayoutScreen()
        DemoScreen.Components -> ComponentsScreen()
    }
}
