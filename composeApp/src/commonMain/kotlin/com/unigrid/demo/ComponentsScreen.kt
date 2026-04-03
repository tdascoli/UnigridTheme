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
import com.unigrid.theme.components.UnigridFilledTextField
import com.unigrid.theme.components.UnigridOutlinedTextField

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

        // Text Fields (M3 style)
        Text(
            text = "Text Fields",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var filledName by remember { mutableStateOf("") }
        var outlinedEmail by remember { mutableStateOf("") }
        var filledAmount by remember { mutableStateOf("") }
        var outlinedBio by remember { mutableStateOf("") }

        Text("Filled", style = MaterialTheme.typography.labelLarge, color = UgMediumGray)
        Spacer(Modifier.height(8.dp))
        UnigridFilledTextField(
            value = filledName,
            onValueChange = { filledName = it },
            label = "Name",
            placeholder = "Enter your name",
            supportingText = "Required field",
        )
        Spacer(Modifier.height(12.dp))
        UnigridFilledTextField(
            value = filledAmount,
            onValueChange = { filledAmount = it },
            label = "Amount",
            prefix = "$",
            suffix = "USD",
            supportingText = "Enter amount in dollars",
        )

        Spacer(Modifier.height(20.dp))

        Text("Outlined", style = MaterialTheme.typography.labelLarge, color = UgMediumGray)
        Spacer(Modifier.height(8.dp))
        UnigridOutlinedTextField(
            value = outlinedEmail,
            onValueChange = { outlinedEmail = it },
            label = "Email",
            placeholder = "you@example.com",
            isError = outlinedEmail.isNotEmpty() && !outlinedEmail.contains("@"),
            supportingText = if (outlinedEmail.isNotEmpty() && !outlinedEmail.contains("@"))
                "Please enter a valid email" else "We'll never share your email",
        )
        Spacer(Modifier.height(12.dp))
        UnigridOutlinedTextField(
            value = outlinedBio,
            onValueChange = { outlinedBio = it },
            label = "Bio",
            placeholder = "Tell us about yourself...",
            singleLine = false,
            minLines = 3,
            maxLength = 200,
        )
        Spacer(Modifier.height(12.dp))
        UnigridOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Disabled field",
            enabled = false,
        )

        Spacer(Modifier.height(32.dp))

        // Legacy Forms
        Text(
            text = "Form Controls",
            style = MaterialTheme.typography.headlineMedium,
            color = UgBlack,
        )
        Spacer(Modifier.height(12.dp))

        var subscribe by remember { mutableStateOf(false) }
        var darkMode by remember { mutableStateOf(false) }
        var contactMethod by remember { mutableStateOf("email") }

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

        Spacer(Modifier.height(24.dp))
    }
}
