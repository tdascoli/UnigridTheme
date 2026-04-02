@file:OptIn(InternalResourceApi::class)

package unigridtheme.unigrid_theme.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/unigridtheme.unigrid_theme.generated.resources/"

internal val Res.font.inter_black: FontResource by lazy {
      FontResource("font:inter_black", setOf(
        ResourceItem(setOf(), "${MD}font/inter_black.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_bold: FontResource by lazy {
      FontResource("font:inter_bold", setOf(
        ResourceItem(setOf(), "${MD}font/inter_bold.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_extrabold: FontResource by lazy {
      FontResource("font:inter_extrabold", setOf(
        ResourceItem(setOf(), "${MD}font/inter_extrabold.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_light: FontResource by lazy {
      FontResource("font:inter_light", setOf(
        ResourceItem(setOf(), "${MD}font/inter_light.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_medium: FontResource by lazy {
      FontResource("font:inter_medium", setOf(
        ResourceItem(setOf(), "${MD}font/inter_medium.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_regular: FontResource by lazy {
      FontResource("font:inter_regular", setOf(
        ResourceItem(setOf(), "${MD}font/inter_regular.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_semibold: FontResource by lazy {
      FontResource("font:inter_semibold", setOf(
        ResourceItem(setOf(), "${MD}font/inter_semibold.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_thin: FontResource by lazy {
      FontResource("font:inter_thin", setOf(
        ResourceItem(setOf(), "${MD}font/inter_thin.ttf", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainFont0Resources(map: MutableMap<String, FontResource>) {
  map.put("inter_black", Res.font.inter_black)
  map.put("inter_bold", Res.font.inter_bold)
  map.put("inter_extrabold", Res.font.inter_extrabold)
  map.put("inter_light", Res.font.inter_light)
  map.put("inter_medium", Res.font.inter_medium)
  map.put("inter_regular", Res.font.inter_regular)
  map.put("inter_semibold", Res.font.inter_semibold)
  map.put("inter_thin", Res.font.inter_thin)
}
