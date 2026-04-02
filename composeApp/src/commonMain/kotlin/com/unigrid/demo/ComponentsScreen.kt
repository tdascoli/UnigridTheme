package com.unigrid.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.unigrid.theme.UgMediumGray
import com.unigrid.theme.UgWhite
import com.unigrid.theme.components.AccordionItem
import com.unigrid.theme.components.TableColumn
import com.unigrid.theme.components.TabItem
import com.unigrid.theme.components.TabVariant
import com.unigrid.theme.components.UnigridAccordion
import com.unigrid.theme.components.UnigridFooter
import com.unigrid.theme.components.UnigridPagination
import com.unigrid.theme.components.UnigridTable
import com.unigrid.theme.components.UnigridTabs

private data class SampleRow(val name: String, val role: String, val status: String)

private val sampleData = listOf(
    SampleRow("Alice", "Engineer", "Active"),
    SampleRow("Bob", "Designer", "Active"),
    SampleRow("Charlie", "Manager", "Away"),
    SampleRow("Diana", "Analyst", "Active"),
)

@Composable
fun ComponentsScreen() {
    Column(Modifier.padding(24.dp)) {
        Text(
            text = "Components",
            style = MaterialTheme.typography.displayMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(24.dp))

        // Table
        Text(
            text = "Table",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridTable(
            columns = listOf(
                TableColumn<SampleRow>(header = "Name") { row ->
                    Text(row.name, style = MaterialTheme.typography.bodyMedium, color = UgBlack)
                },
                TableColumn<SampleRow>(header = "Role") { row ->
                    Text(row.role, style = MaterialTheme.typography.bodyMedium, color = UgBlack)
                },
                TableColumn<SampleRow>(header = "Status") { row ->
                    Text(row.status, style = MaterialTheme.typography.bodyMedium, color = UgBlack)
                },
            ),
            rows = sampleData,
            striped = true,
            bordered = true,
            modifier = Modifier.height(220.dp),
        )

        Spacer(Modifier.height(32.dp))

        // Tabs
        Text(
            text = "Tabs",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var selectedTab by remember { mutableStateOf("overview") }
        val tabs = listOf(
            TabItem("Overview", "overview"),
            TabItem("Details", "details"),
            TabItem("Settings", "settings"),
        )

        UnigridTabs(
            tabs = tabs,
            selectedKey = selectedTab,
            onTabSelected = { selectedTab = it },
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Selected: $selectedTab",
            style = MaterialTheme.typography.bodyMedium,
            color = UgMediumGray,
        )

        Spacer(Modifier.height(32.dp))

        // Accordion
        Text(
            text = "Accordion",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridAccordion(
            items = listOf(
                AccordionItem("What is Unigrid?") {
                    Text(
                        "Unigrid is a Compose Multiplatform design system with brand colors, typography, and reusable components.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgBlack,
                    )
                },
                AccordionItem("How do I use the theme?") {
                    Text(
                        "Wrap your app content in UnigridTheme { ... } to apply the color scheme, typography, and spacing.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgBlack,
                    )
                },
                AccordionItem("Which platforms are supported?") {
                    Text(
                        "Android, iOS, Desktop (JVM), and Web via Compose Multiplatform.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = UgBlack,
                    )
                },
            ),
        )

        Spacer(Modifier.height(32.dp))

        // Pagination
        Text(
            text = "Pagination",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var currentPage by remember { mutableStateOf(1) }
        UnigridPagination(
            currentPage = currentPage,
            totalPages = 5,
            onPageSelected = { currentPage = it },
        )

        Spacer(Modifier.height(32.dp))

        // Footer
        Text(
            text = "Footer",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridFooter(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Unigrid Theme Demo - Built with Compose Multiplatform",
                style = MaterialTheme.typography.bodySmall,
                color = UgWhite,
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}
