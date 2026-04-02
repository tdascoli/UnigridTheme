package com.unigrid.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UgBlack
import com.unigrid.theme.components.CardAccent
import com.unigrid.theme.components.CardBody
import com.unigrid.theme.components.CardSubtitle
import com.unigrid.theme.components.CardTitle
import com.unigrid.theme.components.UnigridCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardsScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Cards",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Accent Colors",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CardAccent.entries.forEach { accent ->
                UnigridCard(
                    modifier = Modifier.width(240.dp),
                    accent = accent,
                    bordered = true,
                ) {
                    CardBody {
                        CardTitle("${accent.name} Accent")
                        CardSubtitle("Sample card with ${accent.name.lowercase()} top border")
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Dark Card",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridCard(
            modifier = Modifier.width(300.dp),
            dark = true,
            accent = CardAccent.Red,
        ) {
            CardBody {
                CardTitle("Dark Card")
                CardSubtitle("A card with dark background and red accent")
            }
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Bordered Card",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridCard(
            modifier = Modifier.width(300.dp),
            bordered = true,
        ) {
            CardBody {
                CardTitle("Bordered")
                CardSubtitle("A simple bordered card without accent color")
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
