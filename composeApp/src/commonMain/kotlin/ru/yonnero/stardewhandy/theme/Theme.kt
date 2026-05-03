package ru.yonnero.stardewhandy.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val stardewColorScheme = lightColorScheme(
    background = StardewBackground,
    surface = StardewCard,
    onSurface = StardewText,
    primary = StardewGreen,
    onPrimary = Color.White,
    outline = StardewBorder
)

@Composable
fun StardewTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = stardewColorScheme,
        content = content
    )
}