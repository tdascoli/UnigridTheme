package com.unigrid.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.unigrid.theme.components.AccordionItem
import com.unigrid.theme.components.TableColumn
import com.unigrid.theme.components.TabItem
import com.unigrid.theme.components.TabVariant
import com.unigrid.theme.components.UnigridAccordion
import com.unigrid.theme.components.UnigridCheckbox
import com.unigrid.theme.components.UnigridErrorText
import com.unigrid.theme.components.UnigridFormGroup
import com.unigrid.theme.components.UnigridHelpText
import com.unigrid.theme.components.UnigridInput
import com.unigrid.theme.components.UnigridLabel
import com.unigrid.theme.components.UnigridPagination
import com.unigrid.theme.components.UnigridRadio
import com.unigrid.theme.components.UnigridSwitch
import com.unigrid.theme.components.UnigridTable
import com.unigrid.theme.components.UnigridTabs
import com.unigrid.theme.components.UnigridTextArea

private data class SampleRow(val name: String, val role: String, val status: String)

private val sampleData = listOf(
    SampleRow("Alice", "Engineer", "1234"),
    SampleRow("Bob", "Designer", "5678"),
    SampleRow("Charlie", "Manager", "9012"),
    SampleRow("Diana", "Analyst", "3456"),
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

        // Forms
        Text(
            text = "Forms",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var subscribe by remember { mutableStateOf(false) }
        var darkMode by remember { mutableStateOf(false) }
        var contactMethod by remember { mutableStateOf("email") }

        UnigridFormGroup {
            UnigridLabel("Name")
            UnigridInput(
                value = name,
                onValueChange = { name = it },
                placeholder = "Enter your name",
            )
        }

        Spacer(Modifier.height(16.dp))

        UnigridFormGroup {
            UnigridLabel("Email")
            UnigridInput(
                value = email,
                onValueChange = { email = it },
                placeholder = "you@example.com",
                isError = email.isNotEmpty() && !email.contains("@"),
            )
            if (email.isNotEmpty() && !email.contains("@")) {
                UnigridErrorText("Please enter a valid email address")
            } else {
                UnigridHelpText("We'll never share your email")
            }
        }

        Spacer(Modifier.height(16.dp))

        UnigridFormGroup {
            UnigridLabel("Message")
            UnigridTextArea(
                value = message,
                onValueChange = { message = it },
                placeholder = "Your message...",
            )
        }

        Spacer(Modifier.height(16.dp))

        UnigridCheckbox(
            checked = subscribe,
            onCheckedChange = { subscribe = it },
            label = "Subscribe to newsletter",
        )

        Spacer(Modifier.height(16.dp))

        UnigridSwitch(
            checked = darkMode,
            onCheckedChange = { darkMode = it },
            label = "Dark mode",
        )

        Spacer(Modifier.height(16.dp))

        UnigridLabel("Preferred contact method")
        Spacer(Modifier.height(8.dp))
        UnigridRadio(
            selected = contactMethod == "email",
            onClick = { contactMethod = "email" },
            label = "Email",
        )
        Spacer(Modifier.height(4.dp))
        UnigridRadio(
            selected = contactMethod == "phone",
            onClick = { contactMethod = "phone" },
            label = "Phone",
        )
        Spacer(Modifier.height(4.dp))
        UnigridRadio(
            selected = contactMethod == "mail",
            onClick = { contactMethod = "mail" },
            label = "Mail",
        )

        Spacer(Modifier.height(32.dp))

        // Table
        Text(
            text = "Table",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        UnigridTable(
            columns = listOf(
                TableColumn(header = "Name") { row ->
                    Text(row.name, style = MaterialTheme.typography.bodyMedium.copy(fontFeatureSettings = "liga, calt, case, tnum, lnum, ss07"), color = UgBlack)
                },
                TableColumn(header = "Role") { row ->
                    Text(row.role, style = MaterialTheme.typography.bodyMedium.copy(fontFeatureSettings = "liga, calt, case, tnum, lnum, ss07"), color = UgBlack)
                },
                TableColumn(header = "Status") { row ->
                    Text(row.status, style = MaterialTheme.typography.bodyMedium.copy(fontFeatureSettings = "liga, calt, case, tnum, lnum, ss07"), color = UgBlack)
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

        Spacer(Modifier.height(24.dp))
    }
}
