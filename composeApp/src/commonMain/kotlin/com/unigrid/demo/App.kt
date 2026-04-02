package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgDarkGray
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgRed
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme
import com.unigrid.theme.components.UnigridNavBar
import com.unigrid.theme.components.UnigridNavItem

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
    Column(Modifier.fillMaxSize()) {
        // Top NavBar with brand + scrollable nav items
        UnigridNavBar(
            dark = true,
            brand = {
                Text(
                    text = "UNIGRID",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = UgWhite,
                )
            },
        ) {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                DemoScreen.entries.forEach { screen ->
                    UnigridNavItem(
                        text = screen.label,
                        onClick = { onScreenSelected(screen) },
                        selected = screen == currentScreen,
                        dark = true,
                    )
                }
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            ScreenContent(currentScreen)
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
            Text(
                text = "UNIGRID",
                style = MaterialTheme.typography.displayMedium,
                color = UgWhite,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
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
