# UnigridTheme Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a Jetpack Compose Multiplatform theme library based on unigrid.css, with custom components and a demo app.

**Architecture:** Two-module KMP project — `unigrid-theme` (library) and `composeApp` (demo). The library wraps Material3's `MaterialTheme` with unigrid design tokens and provides slot-based custom composables. Inter font bundled as compose resource.

**Tech Stack:** Kotlin 2.1.20, Compose Multiplatform 1.8.2, AGP 8.8.2, Gradle 8.11.1, Material3.

---

### Task 1: Gradle Scaffolding

**Files:**
- Create: `gradle/libs.versions.toml`
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`
- Create: `gradle.properties`

**Step 1: Create version catalog**

Create `gradle/libs.versions.toml`:

```toml
[versions]
kotlin = "2.1.20"
compose-multiplatform = "1.8.2"
agp = "8.8.2"

[plugins]
kotlinMultiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
composeMultiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
composeCompiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
androidLibrary = { id = "com.android.library", version.ref = "agp" }
androidApplication = { id = "com.android.application", version.ref = "agp" }
```

**Step 2: Create settings.gradle.kts**

```kotlin
rootProject.name = "UnigridTheme"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolution {
    repositories {
        google()
        mavenCentral()
    }
}

include(":unigrid-theme")
include(":composeApp")
```

**Step 3: Create root build.gradle.kts**

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
}
```

**Step 4: Create gradle.properties**

```properties
org.gradle.jvmargs=-Xmx2048M -Dfile.encoding=UTF-8
kotlin.code.style=official
android.useAndroidX=true
android.nonTransitiveRClass=true
```

**Step 5: Set up Gradle wrapper**

Run: `gradle wrapper --gradle-version 8.11.1`

If Gradle is not installed locally, download the wrapper files manually. The wrapper should produce `gradlew`, `gradlew.bat`, and `gradle/wrapper/gradle-wrapper.properties` with distributionUrl pointing to `gradle-8.11.1-bin.zip`.

**Step 6: Commit**

```bash
git add gradle/ settings.gradle.kts build.gradle.kts gradle.properties gradlew gradlew.bat
git commit -m "feat: add Gradle scaffolding with version catalog"
```

---

### Task 2: Library Module Build Config

**Files:**
- Create: `unigrid-theme/build.gradle.kts`

**Step 1: Create the library module build file**

Create `unigrid-theme/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    jvm("desktop")
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "UnigridTheme"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.ui)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.unigrid.theme"
    compileSdk = 35
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
```

**Step 2: Create source directories**

```bash
mkdir -p unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components
mkdir -p unigrid-theme/src/commonMain/composeResources/font
mkdir -p unigrid-theme/src/commonTest/kotlin/com/unigrid/theme
mkdir -p unigrid-theme/src/androidMain/kotlin
mkdir -p unigrid-theme/src/desktopMain/kotlin
mkdir -p unigrid-theme/src/iosMain/kotlin
```

**Step 3: Verify build**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL (may download dependencies first)

Note: This will fail until we add at least one source file. That's fine — the next task adds source files.

**Step 4: Commit**

```bash
git add unigrid-theme/
git commit -m "feat: add unigrid-theme library module build config"
```

---

### Task 3: Theme Tokens — Colors

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/Color.kt`
- Create: `unigrid-theme/src/commonTest/kotlin/com/unigrid/theme/ColorTest.kt`

**Step 1: Write color tests**

Create `unigrid-theme/src/commonTest/kotlin/com/unigrid/theme/ColorTest.kt`:

```kotlin
package com.unigrid.theme

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun unigridBlackMatchesCssValue() {
        // #1a1a1a = rgb(26, 26, 26)
        assertEquals(Color(0xFF1A1A1A), UgBlack)
    }

    @Test
    fun unigridRedMatchesCssValue() {
        // #c1272d = rgb(193, 39, 45)
        assertEquals(Color(0xFFC1272D), UgRed)
    }

    @Test
    fun unigridWarmGrayMatchesCssValue() {
        assertEquals(Color(0xFFF5F2ED), UgWarmGray)
    }

    @Test
    fun lightColorSchemeHasCorrectPrimary() {
        assertEquals(UgRed, UnigridLightColors.primary)
    }

    @Test
    fun darkColorSchemeHasCorrectBackground() {
        assertEquals(UgBlack, UnigridDarkColors.background)
    }
}
```

**Step 2: Run tests to verify they fail**

Run: `./gradlew :unigrid-theme:desktopTest`
Expected: FAIL — unresolved references

**Step 3: Implement Color.kt**

Create `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/Color.kt`:

```kotlin
package com.unigrid.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Raw unigrid colors — exact hex values from unigrid.css
val UgBlack = Color(0xFF1A1A1A)
val UgWhite = Color(0xFFFFFFFF)
val UgWarmGray = Color(0xFFF5F2ED)
val UgLightGray = Color(0xFFE8E5E0)
val UgMediumGray = Color(0xFF666666)
val UgDarkGray = Color(0xFF333333)
val UgRed = Color(0xFFC1272D)
val UgBrown = Color(0xFF4A3728)
val UgGreen = Color(0xFF2D5A27)
val UgBlue = Color(0xFF274A5A)

@Immutable
data class UnigridColorPalette(
    val black: Color = UgBlack,
    val white: Color = UgWhite,
    val warmGray: Color = UgWarmGray,
    val lightGray: Color = UgLightGray,
    val mediumGray: Color = UgMediumGray,
    val darkGray: Color = UgDarkGray,
    val red: Color = UgRed,
    val brown: Color = UgBrown,
    val green: Color = UgGreen,
    val blue: Color = UgBlue,
)

val LocalUnigridColors = staticCompositionLocalOf { UnigridColorPalette() }

val UnigridLightColors: ColorScheme = lightColorScheme(
    primary = UgRed,
    onPrimary = UgWhite,
    primaryContainer = UgBlue,
    onPrimaryContainer = UgWhite,
    secondary = UgBrown,
    onSecondary = UgWhite,
    secondaryContainer = UgWarmGray,
    onSecondaryContainer = UgDarkGray,
    tertiary = UgGreen,
    onTertiary = UgWhite,
    tertiaryContainer = UgGreen.copy(alpha = 0.12f),
    onTertiaryContainer = UgGreen,
    background = UgWhite,
    onBackground = UgBlack,
    surface = UgWhite,
    onSurface = UgBlack,
    surfaceVariant = UgWarmGray,
    onSurfaceVariant = UgDarkGray,
    outline = UgMediumGray,
    outlineVariant = UgLightGray,
    error = UgRed,
    onError = UgWhite,
    errorContainer = UgRed.copy(alpha = 0.12f),
    onErrorContainer = UgRed,
)

val UnigridDarkColors: ColorScheme = darkColorScheme(
    primary = UgRed,
    onPrimary = UgWhite,
    primaryContainer = UgBlue,
    onPrimaryContainer = UgWhite,
    secondary = UgBrown,
    onSecondary = UgWhite,
    secondaryContainer = UgDarkGray,
    onSecondaryContainer = UgLightGray,
    tertiary = UgGreen,
    onTertiary = UgWhite,
    tertiaryContainer = UgGreen.copy(alpha = 0.2f),
    onTertiaryContainer = Color(0xFF8FBF8A),
    background = UgBlack,
    onBackground = UgWhite,
    surface = UgBlack,
    onSurface = UgWhite,
    surfaceVariant = UgDarkGray,
    onSurfaceVariant = UgLightGray,
    outline = UgMediumGray,
    outlineVariant = UgDarkGray,
    error = Color(0xFFE57373),
    onError = UgBlack,
    errorContainer = UgRed.copy(alpha = 0.3f),
    onErrorContainer = Color(0xFFE57373),
)
```

**Step 4: Run tests**

Run: `./gradlew :unigrid-theme:desktopTest`
Expected: PASS

**Step 5: Commit**

```bash
git add unigrid-theme/src/
git commit -m "feat: add unigrid color tokens and M3 color schemes"
```

---

### Task 4: Theme Tokens — Spacing & Shape

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/Spacing.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/Shape.kt`
- Create: `unigrid-theme/src/commonTest/kotlin/com/unigrid/theme/SpacingTest.kt`

**Step 1: Write spacing tests**

Create `unigrid-theme/src/commonTest/kotlin/com/unigrid/theme/SpacingTest.kt`:

```kotlin
package com.unigrid.theme

import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

class SpacingTest {
    private val spacing = UnigridSpacing()

    @Test
    fun level0IsZero() {
        assertEquals(0.dp, spacing.level0)
    }

    @Test
    fun level3IsOneLeading() {
        assertEquals(26.dp, spacing.level3)
    }

    @Test
    fun level8IsSixTimesLeading() {
        assertEquals(156.dp, spacing.level8)
    }
}
```

**Step 2: Run tests — expect fail**

Run: `./gradlew :unigrid-theme:desktopTest`

**Step 3: Implement Spacing.kt**

```kotlin
package com.unigrid.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class UnigridSpacing(
    val level0: Dp = 0.dp,
    val level1: Dp = 6.5.dp,   // 0.25x leading
    val level2: Dp = 13.dp,    // 0.5x leading
    val level3: Dp = 26.dp,    // 1x leading (base)
    val level4: Dp = 39.dp,    // 1.5x leading
    val level5: Dp = 52.dp,    // 2x leading
    val level6: Dp = 78.dp,    // 3x leading
    val level7: Dp = 104.dp,   // 4x leading
    val level8: Dp = 156.dp,   // 6x leading
)

val LocalUnigridSpacing = staticCompositionLocalOf { UnigridSpacing() }
```

**Step 4: Implement Shape.kt**

```kotlin
package com.unigrid.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val UnigridShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp),
)
```

**Step 5: Run tests**

Run: `./gradlew :unigrid-theme:desktopTest`
Expected: PASS

**Step 6: Commit**

```bash
git add unigrid-theme/src/
git commit -m "feat: add spacing and shape tokens"
```

---

### Task 5: Theme Tokens — Typography + Inter Font

**Files:**
- Create: `unigrid-theme/src/commonMain/composeResources/font/` (font files)
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/Typography.kt`

**Step 1: Download Inter font files**

Download static Inter font files from Google Fonts or the Inter GitHub repository. Place them in `unigrid-theme/src/commonMain/composeResources/font/`:

```
font/
├── inter_regular.ttf
├── inter_medium.ttf
├── inter_semibold.ttf
├── inter_bold.ttf
├── inter_extrabold.ttf
├── inter_black.ttf
├── inter_light.ttf
├── inter_thin.ttf
```

Download from: https://github.com/rsms/inter/releases — use the static TTF files from the `InterDesktop` or `Inter` folder in the release zip.

**Step 2: Implement Typography.kt**

```kotlin
package com.unigrid.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import unigrid_theme.unigrid_theme.generated.resources.*

@Composable
fun InterFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_thin, FontWeight.Thin),
    Font(Res.font.inter_light, FontWeight.Light),
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
    Font(Res.font.inter_extrabold, FontWeight.ExtraBold),
    Font(Res.font.inter_black, FontWeight.Black),
)

@Composable
fun UnigridTypography(): Typography {
    val inter = InterFontFamily()
    return Typography(
        displayLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Black,
            fontSize = 40.sp, // 2.5rem
            lineHeight = 48.sp,
            letterSpacing = (-0.32).sp, // -0.02em
        ),
        displayMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp, // 1.6875rem
            lineHeight = 34.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp, // lg: 1.125rem
            lineHeight = 24.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp, // base: 1rem
            lineHeight = 26.sp, // 1.625
        ),
        bodyMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp, // sm: 0.875rem
            lineHeight = 22.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp, // xs: 0.75rem
            lineHeight = 18.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 14.sp,
        ),
    )
}
```

Note: The exact import path for generated resources depends on the module name. After first build, check the generated `Res` class path. It will be something like `unigrid_theme.unigrid_theme.generated.resources.Res`. Adjust the import accordingly.

**Step 3: Build to verify resource generation**

Run: `./gradlew :unigrid-theme:generateComposeResClass`
Then: `./gradlew :unigrid-theme:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 4: Commit**

```bash
git add unigrid-theme/src/
git commit -m "feat: add Inter font resources and typography tokens"
```

---

### Task 6: UnigridTheme Composable

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/UnigridTheme.kt`

**Step 1: Implement UnigridTheme.kt**

```kotlin
package com.unigrid.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun UnigridTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) UnigridDarkColors else UnigridLightColors

    CompositionLocalProvider(
        LocalUnigridColors provides UnigridColorPalette(),
        LocalUnigridSpacing provides UnigridSpacing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = UnigridTypography(),
            shapes = UnigridShapes,
            content = content,
        )
    }
}

object UnigridTheme {
    val colors: UnigridColorPalette
        @Composable @ReadOnlyComposable
        get() = LocalUnigridColors.current

    val spacing: UnigridSpacing
        @Composable @ReadOnlyComposable
        get() = LocalUnigridSpacing.current
}
```

**Step 2: Build**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 3: Commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/UnigridTheme.kt
git commit -m "feat: add UnigridTheme composable wrapping MaterialTheme"
```

---

### Task 7: Component — UnigridButton

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridButton.kt`

**Step 1: Implement UnigridButton**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unigrid.theme.*

enum class ButtonVariant { Filled, Outline, Ghost }
enum class ButtonSize { Small, Medium, Large }

@Composable
fun UnigridButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled,
    color: Color = UgBlack,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    fullWidth: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor = when (variant) {
        ButtonVariant.Filled -> if (color.isDark()) UgWhite else UgBlack
        ButtonVariant.Outline, ButtonVariant.Ghost -> color
    }

    val containerColor = when (variant) {
        ButtonVariant.Filled -> color
        ButtonVariant.Outline, ButtonVariant.Ghost -> Color.Transparent
    }

    val border = when (variant) {
        ButtonVariant.Filled -> null
        ButtonVariant.Outline -> BorderStroke(2.dp, color)
        ButtonVariant.Ghost -> null
    }

    val verticalPadding = when (size) {
        ButtonSize.Small -> 6.dp
        ButtonSize.Medium -> 10.dp
        ButtonSize.Large -> 14.dp
    }
    val horizontalPadding = when (size) {
        ButtonSize.Small -> 12.dp
        ButtonSize.Medium -> 20.dp
        ButtonSize.Large -> 28.dp
    }
    val fontSize = when (size) {
        ButtonSize.Small -> 12.sp
        ButtonSize.Medium -> 14.sp
        ButtonSize.Large -> 16.sp
    }

    val buttonModifier = if (fullWidth) modifier.fillMaxWidth() else modifier

    Button(
        onClick = onClick,
        modifier = buttonModifier,
        enabled = enabled,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.4f),
            disabledContentColor = contentColor.copy(alpha = 0.4f),
        ),
        border = border,
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding,
        ),
    ) {
        if (icon != null) {
            icon()
            Spacer(Modifier.width(8.dp))
        }
        ProvideTextStyle(
            MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
            )
        ) {
            content()
        }
    }
}

private fun Color.isDark(): Boolean {
    val luminance = 0.299f * red + 0.587f * green + 0.114f * blue
    return luminance < 0.5f
}
```

**Step 2: Build**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 3: Commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridButton.kt
git commit -m "feat: add UnigridButton component with filled/outline/ghost variants"
```

---

### Task 8: Component — UnigridCard

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridCard.kt`

**Step 1: Implement UnigridCard**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

enum class AccentColor(val color: Color) {
    None(Color.Transparent),
    Red(UgRed),
    Brown(UgBrown),
    Green(UgGreen),
    Blue(UgBlue),
}

enum class AspectRatioType(val ratio: Float) {
    Square(1f),
    Landscape(16f / 9f),
    DIN(1.4142f), // sqrt(2), DIN/ISO paper ratio
}

@Composable
fun UnigridCard(
    modifier: Modifier = Modifier,
    accent: AccentColor = AccentColor.None,
    dark: Boolean = false,
    bordered: Boolean = false,
    content: @Composable UnigridCardScope.() -> Unit,
) {
    val bgColor = if (dark) UgBlack else UgWhite
    val contentColor = if (dark) UgWhite else UgBlack

    val shape = RoundedCornerShape(0.dp)
    val borderMod = if (bordered) {
        Modifier.border(2.dp, UgBlack, shape)
    } else {
        Modifier.border(1.dp, UgLightGray, shape)
    }

    val accentMod = if (accent != AccentColor.None) {
        Modifier.drawWithContent {
            drawContent()
            drawRect(
                color = accent.color,
                size = androidx.compose.ui.geometry.Size(size.width, 3.dp.toPx()),
            )
        }
    } else Modifier

    Column(
        modifier = modifier
            .then(borderMod)
            .then(accentMod)
            .background(bgColor, shape)
            .clip(shape)
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyLarge.copy(color = contentColor)) {
            UnigridCardScopeImpl(contentColor).content()
        }
    }
}

interface UnigridCardScope {
    @Composable
    fun CardImage(
        content: @Composable BoxScope.() -> Unit,
        aspectRatio: AspectRatioType? = null,
    )

    @Composable
    fun CardBody(content: @Composable ColumnScope.() -> Unit)

    @Composable
    fun CardTitle(text: String)

    @Composable
    fun CardSubtitle(text: String)

    @Composable
    fun CardFooter(content: @Composable RowScope.() -> Unit)
}

private class UnigridCardScopeImpl(
    private val contentColor: Color,
) : UnigridCardScope {

    @Composable
    override fun CardImage(
        content: @Composable BoxScope.() -> Unit,
        aspectRatio: AspectRatioType?,
    ) {
        val mod = if (aspectRatio != null) {
            Modifier.fillMaxWidth().aspectRatio(aspectRatio.ratio)
        } else {
            Modifier.fillMaxWidth()
        }
        Box(modifier = mod, content = content)
    }

    @Composable
    override fun CardBody(content: @Composable ColumnScope.() -> Unit) {
        Column(
            modifier = Modifier.padding(
                horizontal = UnigridTheme.spacing.level3,
                vertical = UnigridTheme.spacing.level2,
            ),
            content = content,
        )
    }

    @Composable
    override fun CardTitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = contentColor,
        )
    }

    @Composable
    override fun CardSubtitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = UgMediumGray,
        )
    }

    @Composable
    override fun CardFooter(content: @Composable RowScope.() -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = UnigridTheme.spacing.level3,
                    vertical = UnigridTheme.spacing.level2,
                ),
            horizontalArrangement = Arrangement.End,
            content = content,
        )
    }
}
```

Note: The `drawWithContent` call for accent requires an import of `androidx.compose.ui.draw.drawWithContent`. The implementing engineer should add this import and verify the accent border renders as a 3dp colored line at the top of the card.

**Step 2: Build**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 3: Commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridCard.kt
git commit -m "feat: add UnigridCard component with accent colors and aspect ratios"
```

---

### Task 9: Component — UnigridHeader

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridHeader.kt`

**Step 1: Implement**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

enum class HeaderColor(val bg: Color, val fg: Color) {
    Black(UgBlack, UgWhite),
    Brown(UgBrown, UgWhite),
}

@Composable
fun UnigridHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    color: HeaderColor = HeaderColor.Black,
    compact: Boolean = false,
    logo: @Composable (() -> Unit)? = null,
) {
    val verticalPadding = if (compact) UnigridTheme.spacing.level2 else UnigridTheme.spacing.level3

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color.bg)
            .padding(horizontal = UnigridTheme.spacing.level3, vertical = verticalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (logo != null) {
            Box(modifier = Modifier.size(if (compact) 48.dp else 78.dp)) {
                logo()
            }
            Spacer(Modifier.width(UnigridTheme.spacing.level2))
        }

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = color.fg,
                fontWeight = FontWeight.Black,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.fg.copy(alpha = 0.8f),
                )
            }
        }
    }
}
```

**Step 2: Build and commit**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridHeader.kt
git commit -m "feat: add UnigridHeader component"
```

---

### Task 10: Component — UnigridHero

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridHero.kt`

**Step 1: Implement**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

enum class HeroHeight(val fraction: Float) {
    Full(1f),
    Half(0.5f),
    Third(0.33f),
    Auto(0f),
}

enum class HeroAlignment { Top, Center, Bottom }

@Composable
fun UnigridHero(
    modifier: Modifier = Modifier,
    height: HeroHeight = HeroHeight.Half,
    alignment: HeroAlignment = HeroAlignment.Center,
    overlayColor: Color = UgBlack.copy(alpha = 0.5f),
    background: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val heightModifier = when (height) {
        HeroHeight.Auto -> Modifier
        else -> Modifier.fillMaxHeight(height.fraction)
    }

    val contentAlignment = when (alignment) {
        HeroAlignment.Top -> Alignment.TopStart
        HeroAlignment.Center -> Alignment.Center
        HeroAlignment.Bottom -> Alignment.BottomStart
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(heightModifier),
    ) {
        // Background layer
        if (background != null) {
            Box(modifier = Modifier.matchParentSize()) {
                background()
            }
        }

        // Gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            overlayColor.copy(alpha = 0.3f),
                            overlayColor,
                        )
                    )
                ),
        )

        // Content
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(UnigridTheme.spacing.level4),
            contentAlignment = contentAlignment,
        ) {
            ProvideTextStyle(TextStyle(color = UgWhite)) {
                content()
            }
        }
    }
}
```

**Step 2: Build and commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridHero.kt
git commit -m "feat: add UnigridHero component with gradient overlay"
```

---

### Task 11: Component — UnigridSection

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridSection.kt`

**Step 1: Implement**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.unigrid.theme.*

enum class SectionColor(val bg: Color, val fg: Color) {
    Black(UgBlack, UgWhite),
    Brown(UgBrown, UgWhite),
    Red(UgRed, UgWhite),
    Green(UgGreen, UgWhite),
    Blue(UgBlue, UgWhite),
    WarmGray(UgWarmGray, UgBlack),
}

enum class SectionSize { Compact, Default, Large }

@Composable
fun UnigridSection(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    color: SectionColor = SectionColor.Black,
    size: SectionSize = SectionSize.Default,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val verticalPadding = when (size) {
        SectionSize.Compact -> UnigridTheme.spacing.level3
        SectionSize.Default -> UnigridTheme.spacing.level5
        SectionSize.Large -> UnigridTheme.spacing.level7
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color.bg)
            .padding(horizontal = UnigridTheme.spacing.level3, vertical = verticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProvideTextStyle(TextStyle(color = color.fg)) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = color.fg,
                )
            }
            if (subtitle != null) {
                Spacer(Modifier.height(UnigridTheme.spacing.level1))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = color.fg.copy(alpha = 0.85f),
                )
            }
            if (title != null || subtitle != null) {
                Spacer(Modifier.height(UnigridTheme.spacing.level3))
            }
            content()
        }
    }
}
```

**Step 2: Build and commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridSection.kt
git commit -m "feat: add UnigridSection component"
```

---

### Task 12: Component — UnigridNavBar

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridNavBar.kt`

**Step 1: Implement**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

@Composable
fun UnigridNavBar(
    brand: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dark: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    items: @Composable RowScope.() -> Unit,
) {
    val bgColor = if (dark) UgBlack else UgWhite
    val contentColor = if (dark) UgWhite else UgBlack

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(horizontal = UnigridTheme.spacing.level3, vertical = UnigridTheme.spacing.level2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        brand()
        Spacer(Modifier.width(UnigridTheme.spacing.level4))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items()
        }
        actions()
    }
}

@Composable
fun UnigridNavItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = if (selected) FontWeight.Black else FontWeight.Bold,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}
```

**Step 2: Build and commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridNavBar.kt
git commit -m "feat: add UnigridNavBar and UnigridNavItem components"
```

---

### Task 13: Components — Grid & Container

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridGrid.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridContainer.kt`

**Step 1: Implement UnigridContainer**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unigrid.theme.UnigridTheme

enum class ContainerWidth(val maxWidth: Dp) {
    Narrow(1280.dp),
    Wide(1600.dp),
    Fluid(Dp.Infinity),
}

@Composable
fun UnigridContainer(
    modifier: Modifier = Modifier,
    width: ContainerWidth = ContainerWidth.Narrow,
    flush: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val horizontalPadding = if (flush) 0.dp else UnigridTheme.spacing.level3

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = if (width == ContainerWidth.Fluid) Dp.Unspecified else width.maxWidth)
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            content = content,
        )
    }
}
```

**Step 2: Implement UnigridGrid**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

private const val GRID_COLUMNS = 12

@Composable
fun UnigridGrid(
    modifier: Modifier = Modifier,
    gap: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier.fillMaxWidth(),
    ) { measurables, constraints ->
        val gapPx = gap.roundToPx()
        val totalGapWidth = gapPx * (GRID_COLUMNS - 1)
        val columnWidth = (constraints.maxWidth - totalGapWidth) / GRID_COLUMNS

        val placeables = measurables.map { measurable ->
            val span = measurable.parentData?.let { (it as? GridItemData)?.span } ?: GRID_COLUMNS
            val startCol = measurable.parentData?.let { (it as? GridItemData)?.start } ?: -1
            val itemWidth = columnWidth * span + gapPx * (span - 1)
            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = itemWidth,
                    maxWidth = itemWidth,
                )
            )
            Triple(placeable, span, startCol)
        }

        // Simple row-based layout
        var currentCol = 0
        var currentY = 0
        var rowHeight = 0
        val positions = mutableListOf<Triple<Int, Int, Int>>() // x, y, height

        for ((placeable, span, startCol) in placeables) {
            val col = if (startCol >= 0) startCol - 1 else currentCol
            if (col + span > GRID_COLUMNS) {
                currentY += rowHeight + gapPx
                rowHeight = 0
                currentCol = 0
            }
            val actualCol = if (startCol >= 0) startCol - 1 else currentCol
            val x = actualCol * (columnWidth + gapPx)
            positions.add(Triple(x, currentY, placeable.height))
            rowHeight = max(rowHeight, placeable.height)
            currentCol = actualCol + span
        }

        val totalHeight = currentY + rowHeight

        layout(constraints.maxWidth, totalHeight) {
            placeables.forEachIndexed { index, (placeable, _, _) ->
                val (x, y, _) = positions[index]
                placeable.placeRelative(x, y)
            }
        }
    }
}

data class GridItemData(val span: Int = GRID_COLUMNS, val start: Int = -1)

fun Modifier.gridSpan(span: Int): Modifier = this.then(
    object : androidx.compose.ui.layout.ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any {
            return (parentData as? GridItemData ?: GridItemData()).copy(span = span)
        }
    }
)

fun Modifier.gridStart(column: Int): Modifier = this.then(
    object : androidx.compose.ui.layout.ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any {
            return (parentData as? GridItemData ?: GridItemData()).copy(start = column)
        }
    }
)
```

**Step 3: Build and commit**

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridContainer.kt
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridGrid.kt
git commit -m "feat: add UnigridGrid (12-column) and UnigridContainer layout components"
```

---

### Task 14: Components — Table, Tabs, Accordion, Footer, Pagination

**Files:**
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridTable.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridTabs.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridAccordion.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridFooter.kt`
- Create: `unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/UnigridPagination.kt`

**Step 1: Implement UnigridTable**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

data class TableColumn(
    val header: String,
    val weight: Float = 1f,
    val alignment: TextAlign = TextAlign.Start,
)

@Composable
fun UnigridTable(
    columns: List<TableColumn>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier,
    striped: Boolean = false,
    bordered: Boolean = false,
    compact: Boolean = false,
    dark: Boolean = false,
) {
    val bgColor = if (dark) UgBlack else UgWhite
    val fgColor = if (dark) UgWhite else UgBlack
    val headerBg = if (dark) UgDarkGray else UgLightGray
    val stripeBg = if (dark) UgDarkGray.copy(alpha = 0.3f) else UgWarmGray
    val vertPad = if (compact) 4.dp else 10.dp
    val horizPad = if (compact) 8.dp else 12.dp
    val borderMod = if (bordered) Modifier.border(1.dp, UgLightGray) else Modifier

    Column(modifier = modifier.fillMaxWidth().then(borderMod).background(bgColor)) {
        // Header row
        Row(modifier = Modifier.fillMaxWidth().background(headerBg)) {
            columns.forEach { col ->
                Text(
                    text = col.header.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = fgColor,
                    textAlign = col.alignment,
                    modifier = Modifier
                        .weight(col.weight)
                        .padding(horizontal = horizPad, vertical = vertPad),
                )
            }
        }
        // Data rows
        rows.forEachIndexed { rowIndex, row ->
            val rowBg = if (striped && rowIndex % 2 == 1) stripeBg else bgColor
            Row(modifier = Modifier.fillMaxWidth().background(rowBg)) {
                row.forEachIndexed { colIndex, cell ->
                    val col = columns.getOrNull(colIndex)
                    Text(
                        text = cell,
                        style = MaterialTheme.typography.bodyMedium,
                        color = fgColor,
                        textAlign = col?.alignment ?: TextAlign.Start,
                        modifier = Modifier
                            .weight(col?.weight ?: 1f)
                            .padding(horizontal = horizPad, vertical = vertPad),
                    )
                }
            }
        }
    }
}
```

**Step 2: Implement UnigridTabs**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

enum class TabsVariant { Default, Bordered, Pills }

data class TabItem(val label: String, val key: String = label)

@Composable
fun UnigridTabs(
    tabs: List<TabItem>,
    selectedKey: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: TabsVariant = TabsVariant.Default,
    vertical: Boolean = false,
) {
    val arrangement = Arrangement.spacedBy(if (variant == TabsVariant.Pills) 4.dp else 0.dp)

    if (vertical) {
        Column(modifier = modifier, verticalArrangement = arrangement) {
            tabs.forEach { tab ->
                TabContent(tab, tab.key == selectedKey, variant) { onTabSelected(tab.key) }
            }
        }
    } else {
        Row(modifier = modifier, horizontalArrangement = arrangement) {
            tabs.forEach { tab ->
                TabContent(tab, tab.key == selectedKey, variant) { onTabSelected(tab.key) }
            }
        }
    }
}

@Composable
private fun TabContent(
    tab: TabItem,
    selected: Boolean,
    variant: TabsVariant,
    onClick: () -> Unit,
) {
    val shape = when (variant) {
        TabsVariant.Pills -> RoundedCornerShape(4.dp)
        else -> RoundedCornerShape(0.dp)
    }

    val bgColor = when {
        selected && variant == TabsVariant.Pills -> UgBlack
        selected -> UgWhite
        else -> Color.Transparent
    }

    val fgColor = when {
        selected && variant == TabsVariant.Pills -> UgWhite
        else -> UgBlack
    }

    val borderMod = when {
        variant == TabsVariant.Bordered && selected -> Modifier.border(2.dp, UgBlack, shape)
        variant == TabsVariant.Bordered -> Modifier.border(1.dp, UgLightGray, shape)
        else -> Modifier
    }

    Text(
        text = tab.label,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        color = fgColor,
        modifier = Modifier
            .then(borderMod)
            .clip(shape)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
    )
}
```

Note: Add `import androidx.compose.ui.graphics.Color` for `Color.Transparent`.

**Step 3: Implement UnigridAccordion**

```kotlin
package com.unigrid.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

data class AccordionItem(
    val title: String,
    val key: String = title,
)

@Composable
fun UnigridAccordion(
    items: List<AccordionItem>,
    modifier: Modifier = Modifier,
    content: @Composable (key: String) -> Unit,
) {
    var expandedKey by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.fillMaxWidth()) {
        items.forEach { item ->
            val isExpanded = expandedKey == item.key
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, UgLightGray),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedKey = if (isExpanded) null else item.key }
                        .padding(UnigridTheme.spacing.level2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (isExpanded) "−" else "+",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.width(24.dp),
                    )
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                AnimatedVisibility(visible = isExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = UnigridTheme.spacing.level3,
                                end = UnigridTheme.spacing.level3,
                                bottom = UnigridTheme.spacing.level2,
                            ),
                    ) {
                        content(item.key)
                    }
                }
            }
        }
    }
}
```

**Step 4: Implement UnigridFooter**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.unigrid.theme.*

@Composable
fun UnigridFooter(
    modifier: Modifier = Modifier,
    dark: Boolean = true,
    compact: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val bgColor = if (dark) UgBlack else UgWarmGray
    val fgColor = if (dark) UgWhite else UgBlack
    val vertPad = if (compact) UnigridTheme.spacing.level2 else UnigridTheme.spacing.level4

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(horizontal = UnigridTheme.spacing.level3, vertical = vertPad),
    ) {
        ProvideTextStyle(TextStyle(color = fgColor)) {
            content()
        }
    }
}
```

**Step 5: Implement UnigridPagination**

```kotlin
package com.unigrid.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

@Composable
fun UnigridPagination(
    currentPage: Int,
    totalPages: Int,
    onPageSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Previous
        PaginationItem(
            text = "\u2190", // left arrow
            selected = false,
            enabled = currentPage > 1,
            onClick = { onPageSelected(currentPage - 1) },
        )

        // Page numbers
        for (page in 1..totalPages) {
            PaginationItem(
                text = page.toString(),
                selected = page == currentPage,
                enabled = true,
                onClick = { onPageSelected(page) },
            )
        }

        // Next
        PaginationItem(
            text = "\u2192", // right arrow
            selected = false,
            enabled = currentPage < totalPages,
            onClick = { onPageSelected(currentPage + 1) },
        )
    }
}

@Composable
private fun PaginationItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val bgColor = if (selected) UgBlack else UgWhite
    val fgColor = if (selected) UgWhite else if (enabled) UgBlack else UgMediumGray

    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        color = fgColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .border(1.dp, UgLightGray)
            .background(bgColor)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}
```

**Step 6: Build and commit**

Run: `./gradlew :unigrid-theme:compileKotlinDesktop`

```bash
git add unigrid-theme/src/commonMain/kotlin/com/unigrid/theme/components/
git commit -m "feat: add Table, Tabs, Accordion, Footer, and Pagination components"
```

---

### Task 15: Demo App — Build Config & Scaffolding

**Files:**
- Create: `composeApp/build.gradle.kts`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/App.kt`
- Create: `composeApp/src/desktopMain/kotlin/com/unigrid/demo/Main.kt`
- Create: `composeApp/src/androidMain/kotlin/com/unigrid/demo/MainActivity.kt`
- Create: `composeApp/src/androidMain/AndroidManifest.xml`

**Step 1: Create composeApp/build.gradle.kts**

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidApplication)
}

kotlin {
    androidTarget()
    jvm("desktop")
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":unigrid-theme"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.ui)
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.unigrid.demo"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.unigrid.demo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.desktop {
    application {
        mainClass = "com.unigrid.demo.MainKt"
        nativeDistributions {
            packageName = "UnigridDemo"
            packageVersion = "1.0.0"
        }
    }
}
```

**Step 2: Create source directories**

```bash
mkdir -p composeApp/src/commonMain/kotlin/com/unigrid/demo/screens
mkdir -p composeApp/src/desktopMain/kotlin/com/unigrid/demo
mkdir -p composeApp/src/androidMain/kotlin/com/unigrid/demo
mkdir -p composeApp/src/iosMain/kotlin/com/unigrid/demo
```

**Step 3: Create AndroidManifest.xml**

Create `composeApp/src/androidMain/AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:label="Unigrid Demo"
        android:supportsRtl="true"
        android:theme="@android:style/Theme.Material.Light.NoActionBar">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

**Step 4: Create platform entry points**

Desktop `composeApp/src/desktopMain/kotlin/com/unigrid/demo/Main.kt`:

```kotlin
package com.unigrid.demo

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Unigrid Theme Demo",
        state = rememberWindowState(width = 1280.dp, height = 800.dp),
    ) {
        App()
    }
}
```

Android `composeApp/src/androidMain/kotlin/com/unigrid/demo/MainActivity.kt`:

```kotlin
package com.unigrid.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
```

iOS `composeApp/src/iosMain/kotlin/com/unigrid/demo/MainViewController.kt`:

```kotlin
package com.unigrid.demo

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App() }
```

**Step 5: Create App.kt skeleton**

Create `composeApp/src/commonMain/kotlin/com/unigrid/demo/App.kt`:

```kotlin
package com.unigrid.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.unigrid.theme.UnigridTheme

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
    var currentScreen by remember { mutableStateOf(DemoScreen.Home) }

    UnigridTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            DemoSidebar(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it },
            )
            // Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
            ) {
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
        }
    }
}
```

**Step 6: Commit**

```bash
git add composeApp/
git commit -m "feat: add demo app scaffolding with platform entry points"
```

---

### Task 16: Demo App — Sidebar & Screen Stubs

**Files:**
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/DemoSidebar.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/HomeScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/ColorsScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/TypographyScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/SpacingScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/ButtonsScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/CardsScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/LayoutScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/unigrid/demo/screens/ComponentsScreen.kt`

**Step 1: Implement DemoSidebar**

```kotlin
package com.unigrid.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*

@Composable
fun DemoSidebar(
    currentScreen: DemoScreen,
    onScreenSelected: (DemoScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(220.dp)
            .fillMaxHeight()
            .background(UgBlack)
            .padding(vertical = UnigridTheme.spacing.level3),
    ) {
        Text(
            text = "UNIGRID",
            style = MaterialTheme.typography.headlineLarge,
            color = UgWhite,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = UnigridTheme.spacing.level3),
        )
        Spacer(Modifier.height(UnigridTheme.spacing.level2))
        Text(
            text = "Theme Demo",
            style = MaterialTheme.typography.bodyMedium,
            color = UgMediumGray,
            modifier = Modifier.padding(horizontal = UnigridTheme.spacing.level3),
        )
        Spacer(Modifier.height(UnigridTheme.spacing.level4))

        DemoScreen.entries.forEach { screen ->
            val selected = screen == currentScreen
            Text(
                text = screen.label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.Black else FontWeight.Normal,
                color = if (selected) UgRed else UgWhite,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onScreenSelected(screen) }
                    .background(if (selected) UgDarkGray else UgBlack)
                    .padding(horizontal = UnigridTheme.spacing.level3, vertical = 12.dp),
            )
        }
    }
}
```

**Step 2: Implement each demo screen**

Each screen should showcase the relevant theme tokens or components. Below are key implementations. The implementing engineer should follow these patterns for all screens.

**HomeScreen.kt** — Hero + Header + intro cards:

```kotlin
package com.unigrid.demo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unigrid.theme.*
import com.unigrid.theme.components.*

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        UnigridHeader(
            title = "Unigrid Theme",
            subtitle = "A Compose Multiplatform design system inspired by Massimo Vignelli",
            color = HeaderColor.Black,
        )

        UnigridSection(
            title = "Design System",
            subtitle = "Based on unigrid.css — the Unigrid system for the US National Park Service",
            color = SectionColor.WarmGray,
        )

        UnigridContainer {
            Spacer(Modifier.height(UnigridTheme.spacing.level4))
            Text(
                text = "Components",
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(Modifier.height(UnigridTheme.spacing.level2))
            Text(
                text = "Explore the theme tokens and components using the sidebar navigation.",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(UnigridTheme.spacing.level5))
        }
    }
}
```

**ColorsScreen.kt** — Display all 10 colors with swatches:

```kotlin
package com.unigrid.demo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unigrid.theme.*
import com.unigrid.theme.components.UnigridContainer

private data class ColorSwatch(val name: String, val hex: String, val color: Color, val darkText: Boolean = false)

private val swatches = listOf(
    ColorSwatch("Black", "#1a1a1a", UgBlack),
    ColorSwatch("White", "#ffffff", UgWhite, darkText = true),
    ColorSwatch("Warm Gray", "#f5f2ed", UgWarmGray, darkText = true),
    ColorSwatch("Light Gray", "#e8e5e0", UgLightGray, darkText = true),
    ColorSwatch("Medium Gray", "#666666", UgMediumGray),
    ColorSwatch("Dark Gray", "#333333", UgDarkGray),
    ColorSwatch("Red", "#c1272d", UgRed),
    ColorSwatch("Brown", "#4a3728", UgBrown),
    ColorSwatch("Green", "#2d5a27", UgGreen),
    ColorSwatch("Blue", "#274a5a", UgBlue),
)

@Composable
fun ColorsScreen() {
    UnigridContainer {
        Spacer(Modifier.height(UnigridTheme.spacing.level4))
        Text("Colors", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(UnigridTheme.spacing.level3))

        swatches.forEach { swatch ->
            val textColor = if (swatch.darkText) UgBlack else UgWhite
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(swatch.color)
                        .border(1.dp, UgLightGray),
                )
                Spacer(Modifier.width(UnigridTheme.spacing.level2))
                Column {
                    Text(swatch.name, style = MaterialTheme.typography.titleMedium)
                    Text(swatch.hex, style = MaterialTheme.typography.bodySmall, color = UgMediumGray)
                }
            }
        }
        Spacer(Modifier.height(UnigridTheme.spacing.level5))
    }
}
```

**ButtonsScreen.kt** — All button variants:

```kotlin
package com.unigrid.demo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unigrid.theme.*
import com.unigrid.theme.components.*

@Composable
fun ButtonsScreen() {
    UnigridContainer {
        Spacer(Modifier.height(UnigridTheme.spacing.level4))
        Text("Buttons", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(UnigridTheme.spacing.level3))

        Text("Filled", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(UnigridTheme.spacing.level2))
        Row(horizontalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level2)) {
            UnigridButton(onClick = {}) { Text("Default") }
            UnigridButton(onClick = {}, color = UgRed) { Text("Red") }
            UnigridButton(onClick = {}, color = UgBrown) { Text("Brown") }
            UnigridButton(onClick = {}, color = UgGreen) { Text("Green") }
            UnigridButton(onClick = {}, color = UgBlue) { Text("Blue") }
        }

        Spacer(Modifier.height(UnigridTheme.spacing.level3))
        Text("Outline", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(UnigridTheme.spacing.level2))
        Row(horizontalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level2)) {
            UnigridButton(onClick = {}, variant = ButtonVariant.Outline) { Text("Default") }
            UnigridButton(onClick = {}, variant = ButtonVariant.Outline, color = UgRed) { Text("Red") }
            UnigridButton(onClick = {}, variant = ButtonVariant.Outline, color = UgBrown) { Text("Brown") }
        }

        Spacer(Modifier.height(UnigridTheme.spacing.level3))
        Text("Ghost", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(UnigridTheme.spacing.level2))
        Row(horizontalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level2)) {
            UnigridButton(onClick = {}, variant = ButtonVariant.Ghost) { Text("Default") }
            UnigridButton(onClick = {}, variant = ButtonVariant.Ghost, color = UgRed) { Text("Red") }
        }

        Spacer(Modifier.height(UnigridTheme.spacing.level3))
        Text("Sizes", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(UnigridTheme.spacing.level2))
        Row(
            horizontalArrangement = Arrangement.spacedBy(UnigridTheme.spacing.level2),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            UnigridButton(onClick = {}, size = ButtonSize.Small) { Text("Small") }
            UnigridButton(onClick = {}, size = ButtonSize.Medium) { Text("Medium") }
            UnigridButton(onClick = {}, size = ButtonSize.Large) { Text("Large") }
        }

        Spacer(Modifier.height(UnigridTheme.spacing.level5))
    }
}
```

**Remaining screens:** Follow the same pattern. Each screen wraps in `UnigridContainer`, shows a title, then demonstrates the relevant tokens/components. The implementing engineer should create:

- **TypographyScreen** — Show each M3 text style with its name and a sample line
- **SpacingScreen** — Show each spacing level as a colored bar with its dp value
- **CardsScreen** — Show cards with different accents, dark mode, horizontal variant
- **LayoutScreen** — Show UnigridGrid with items spanning different columns
- **ComponentsScreen** — Show Table, Tabs, Accordion, Pagination, Footer

**Step 3: Update App.kt imports**

Add imports in `App.kt` for the screen functions (they need to be top-level `@Composable fun` in the `com.unigrid.demo.screens` package, imported as `com.unigrid.demo.screens.*`). Alternatively, move them to the `com.unigrid.demo` package.

**Step 4: Build the desktop target**

Run: `./gradlew :composeApp:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 5: Run the desktop demo**

Run: `./gradlew :composeApp:run`
Expected: Window opens showing the demo app with sidebar and home screen.

**Step 6: Commit**

```bash
git add composeApp/
git commit -m "feat: add demo app with sidebar navigation and all showcase screens"
```

---

### Task 17: Final — Full Build & Test

**Step 1: Run all tests**

Run: `./gradlew :unigrid-theme:desktopTest`
Expected: All tests PASS

**Step 2: Build all targets**

Run: `./gradlew :unigrid-theme:assemble :composeApp:compileKotlinDesktop`
Expected: BUILD SUCCESSFUL

**Step 3: Run the demo**

Run: `./gradlew :composeApp:run`
Expected: Demo app opens, all screens render correctly.

**Step 4: Fix any compilation issues**

Address any import issues, missing resources, or API mismatches. Common issues:
- Generated `Res` class import path may differ from plan — check `build/` output
- Some Compose APIs may have slightly different signatures in 1.8.2
- Ensure all font files are correctly named (lowercase, underscores, no hyphens)

**Step 5: Final commit**

```bash
git add -A
git commit -m "fix: resolve build issues and finalize library"
```

---

## Summary

| Task | Description | Key Files |
|------|------------|-----------|
| 1 | Gradle scaffolding | `settings.gradle.kts`, `build.gradle.kts`, `gradle/` |
| 2 | Library module build | `unigrid-theme/build.gradle.kts` |
| 3 | Color tokens | `Color.kt`, `ColorTest.kt` |
| 4 | Spacing & Shape tokens | `Spacing.kt`, `Shape.kt`, `SpacingTest.kt` |
| 5 | Typography + font | `Typography.kt`, font files |
| 6 | UnigridTheme composable | `UnigridTheme.kt` |
| 7 | Button component | `UnigridButton.kt` |
| 8 | Card component | `UnigridCard.kt` |
| 9 | Header component | `UnigridHeader.kt` |
| 10 | Hero component | `UnigridHero.kt` |
| 11 | Section component | `UnigridSection.kt` |
| 12 | NavBar component | `UnigridNavBar.kt` |
| 13 | Grid & Container | `UnigridGrid.kt`, `UnigridContainer.kt` |
| 14 | Table, Tabs, Accordion, Footer, Pagination | 5 component files |
| 15 | Demo app scaffolding | `composeApp/build.gradle.kts`, entry points |
| 16 | Demo screens | Sidebar + 8 demo screens |
| 17 | Final build & test | Full verification |
