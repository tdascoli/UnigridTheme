package com.unigrid.demo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.components.HeaderBackground
import com.unigrid.theme.components.SectionColor
import com.unigrid.theme.components.UnigridContainer
import com.unigrid.theme.components.UnigridHeader
import com.unigrid.theme.components.UnigridSection

@Composable
fun HomeScreen() {
    UnigridHeader(
        title = "Unigrid Theme",
        subtitle = "A Compose Multiplatform design system",
        background = HeaderBackground.Black,
    )

    UnigridSection(
        color = SectionColor.WarmGray,
        title = "Welcome",
        subtitle = "Explore the Unigrid design system components",
    )

    UnigridContainer {
        Spacer(Modifier.height(24.dp))
        Text(
            text = "This demo app showcases all the tokens and components available in the Unigrid Theme library. " +
                "Use the sidebar navigation to explore colors, typography, spacing, buttons, cards, layout primitives, " +
                "and higher-level components like tables, tabs, accordions, and pagination.",
            style = MaterialTheme.typography.bodyLarge,
            color = UgBlack,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "The theme is built on Material 3, extended with Unigrid's brand palette, Inter font family, " +
                "and a modular-scale spacing system derived from a 26dp baseline.",
            style = MaterialTheme.typography.bodyMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))
    }
}
