package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unigrid.theme.UgBlack
import com.unigrid.theme.UgBrown
import com.unigrid.theme.UgWhite
import com.unigrid.theme.UnigridTheme

enum class HeaderBackground(val bg: Color) {
    Black(UgBlack),
    Brown(UgBrown),
}

@Composable
fun UnigridHeader(
    modifier: Modifier = Modifier,
    background: HeaderBackground = HeaderBackground.Black,
    compact: Boolean = false,
    logo: (@Composable () -> Unit)? = null,
    title: String? = null,
    subtitle: String? = null,
) {
    val spacing = UnigridTheme.spacing
    val vPadding = if (compact) spacing.level3 else spacing.level5
    val hPadding = spacing.level4

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background.bg)
            .padding(horizontal = hPadding, vertical = vPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        logo?.let {
            it()
            Spacer(Modifier.width(spacing.level3))
        }
        Column {
            title?.let {
                Text(
                    text = it,
                    style = if (compact) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.displayMedium,
                    color = UgWhite,
                )
            }
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = UgWhite.copy(alpha = 0.7f),
                )
            }
        }
    }
}
