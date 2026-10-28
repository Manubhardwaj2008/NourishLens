package com.example.nourishlens.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BotanicalColorScheme = lightColorScheme(
    primary = ForestPrimary,
    onPrimary = OnForestPrimary,
    primaryContainer = ForestContainer,
    onPrimaryContainer = OnForestContainer,
    secondary = TerracottaSecondary,
    onSecondary = OnTerracottaSecondary,
    secondaryContainer = TerracottaContainer,
    tertiary = HerbalMintText,
    onTertiary = Color.White,
    background = SurfaceWarmOat,
    onBackground = CharcoalEspresso,
    surface = SurfaceWarmOat,
    onSurface = CharcoalEspresso,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = CharcoalVariant,
    outline = OutlineHairline,
    error = NonComplianceRed,
    errorContainer = NonComplianceRedBg,
    onError = Color.White,
    onErrorContainer = NonComplianceRedText
)

@Composable
fun NourishLensTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BotanicalColorScheme,
        typography = Typography,
        content = content
    )
}
