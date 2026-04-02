# UnigridTheme — Jetpack Compose Multiplatform Design

**Date:** 2026-04-01
**Status:** Approved

## Goal

Create a Jetpack Compose Multiplatform theme library based on the [unigrid.css](https://github.com/tdascoli/unigrid.css) framework — a design system inspired by Massimo Vignelli's Unigrid system for the US National Park Service.

## Decisions

- **Scope:** Full design system — theme tokens + custom components + demo app
- **Platforms:** Android, Desktop (JVM), iOS
- **Foundation:** Extend Material3 (`MaterialTheme`) with unigrid overrides
- **Architecture:** Approach A — two-module KMP library (`unigrid-theme` + `composeApp`)

## Project Structure

```
UnigridTheme/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/libs.versions.toml
├── unigrid-theme/                    # Library module
│   ├── build.gradle.kts
│   └── src/commonMain/kotlin/com/unigrid/theme/
│       ├── UnigridTheme.kt           # Main theme composable
│       ├── Color.kt                  # 10 unigrid colors + M3 color schemes
│       ├── Typography.kt             # Inter font, 7-tier scale, heading styles
│       ├── Spacing.kt                # 9-level spacing system (CompositionLocal)
│       ├── Shape.kt                  # Sharp editorial shapes
│       └── components/
│           ├── UnigridButton.kt
│           ├── UnigridCard.kt
│           ├── UnigridHeader.kt
│           ├── UnigridHero.kt
│           ├── UnigridSection.kt
│           ├── UnigridNavBar.kt
│           ├── UnigridGrid.kt
│           ├── UnigridContainer.kt
│           ├── UnigridTable.kt
│           ├── UnigridTabs.kt
│           ├── UnigridAccordion.kt
│           ├── UnigridFooter.kt
│           └── UnigridPagination.kt
│   └── src/commonMain/composeResources/font/
│       └── inter_variable.ttf
├── composeApp/                       # Demo app
│   ├── build.gradle.kts
│   ├── src/commonMain/
│   ├── src/androidMain/
│   ├── src/desktopMain/
│   └── src/iosMain/
├── iosApp/                           # iOS Xcode runner
```

**Kotlin:** 2.1.x, **Compose Multiplatform:** 1.7.x, **Gradle:** 8.x with version catalog.

## Theme System

### Colors

10 unigrid colors mapped to M3 color schemes (light + dark):

| Unigrid Token    | Hex       | M3 Light Role              | M3 Dark Role               |
|------------------|-----------|----------------------------|-----------------------------|
| `black`          | #1a1a1a   | onBackground, onSurface    | background, surface         |
| `white`          | #ffffff   | background, surface        | onBackground, onSurface     |
| `warm-gray`      | #f5f2ed   | surfaceVariant             | surfaceVariant (darkened)   |
| `red`            | #c1272d   | primary, error             | primary, error              |
| `brown`          | #4a3728   | secondary                  | secondary                   |
| `green`          | #2d5a27   | tertiary                   | tertiary                    |
| `blue`           | #274a5a   | primaryContainer           | primaryContainer            |
| `medium-gray`    | #666666   | outline                    | outline                     |
| `dark-gray`      | #333333   | onSurfaceVariant           | surfaceVariant              |
| `light-gray`     | #e8e5e0   | outlineVariant             | outlineVariant              |

Raw colors accessible via `UnigridColors` CompositionLocal for direct use.

### Typography

Inter font (bundled as multiplatform resource), mapped to M3 Typography:

| M3 Role             | Unigrid Mapping                          |
|---------------------|------------------------------------------|
| displayLarge        | H1: 2.5rem, weight 900, -0.02em         |
| displayMedium       | H2: 1.6875rem, weight 700               |
| displaySmall        | H3                                       |
| headlineLarge/Med/Sm| H4–H6                                   |
| titleLarge          | lg 1.125rem, semi-bold                   |
| bodyLarge           | base 1rem, line-height 1.625            |
| bodyMedium          | sm 0.875rem                              |
| bodySmall           | xs 0.75rem (caption)                     |
| labelLarge/Med/Sm   | uppercase labels, varying sizes          |

### Spacing

Custom `CompositionLocal<UnigridSpacing>` — 9 levels based on leading multiplier:

| Level | Multiplier | Value (dp) |
|-------|------------|------------|
| 0     | 0x         | 0          |
| 1     | 0.25x      | 6.5        |
| 2     | 0.5x       | 13         |
| 3     | 1x         | 26         |
| 4     | 1.5x       | 39         |
| 5     | 2x         | 52         |
| 6     | 3x         | 78         |
| 7     | 4x         | 104        |
| 8     | 6x         | 156        |

Access: `UnigridTheme.spacing.level3`

### Shape

Sharp/editorial aesthetic: `RoundedCornerShape(0.dp)` for most, `2.dp` for small elements.

## Components

| Component            | CSS Source       | Key Features                                                        |
|----------------------|------------------|---------------------------------------------------------------------|
| UnigridButton        | .ug-btn          | Filled/outline/ghost, 6 colors, sm/lg, icon support                |
| UnigridCard          | .ug-card         | Image (square/landscape/DIN), body, footer, accent, horizontal     |
| UnigridHeader        | .ug-header       | Full-width black/brown band, logo, title, subtitle                 |
| UnigridHero          | .ug-hero         | Background image, gradient overlay, full/half/third height         |
| UnigridSection       | .ug-section      | Colored band, title/subtitle, compact/large                        |
| UnigridNavBar        | .ug-navbar       | Brand + nav items, dark variant                                    |
| UnigridGrid          | .ug-grid         | 12-column grid layout helper                                      |
| UnigridContainer     | .ug-container    | Content container, narrow/wide/fluid                               |
| UnigridTable         | .ug-table        | Striped/bordered/compact, dark mode                                |
| UnigridTabs          | .ug-tabs         | Bordered/pills/vertical variants                                   |
| UnigridAccordion     | .ug-accordion    | Expandable sections                                                |
| UnigridFooter        | .ug-footer       | Sticky footer, brand, nav links                                   |
| UnigridPagination    | .ug-pagination   | Page navigation                                                    |

API style: slot-based, idiomatic Compose with enum variants.

## Demo App

Catalog-style navigation with screens: Home, Colors, Typography, Spacing, Buttons, Cards, Layout, Components.

- Desktop: sidebar navigation, ~1280x800 window
- Android/iOS: bottom nav or drawer
- Each screen is scrollable and self-contained

Platform runners: Android `MainActivity`, Desktop `Window`, iOS `ComposeUIViewController` in SwiftUI wrapper.

## Excluded

- Dropdown (use M3 DropdownMenu)
- Scrollspy (JS-dependent)
- Sidebar hamburger toggle (web-specific)
- Format system (print-specific DIN panels)
