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
