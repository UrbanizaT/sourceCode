package com.example.urbanizat.ui.theme
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = darkBlue,            // azul marino como principal
    onPrimary = whiteTextLight,
    secondary = lightBlue,         // azul claro como secundario
    onSecondary = whiteTextLight,
    background = background,
    onBackground = grayText,
    surfaceVariant = Color(0xFFDCEEF8),  // azul muy claro para las cards
    onSurfaceVariant = grayText,
    tertiary = green,
    onTertiary = whiteTextLight,
    error = Color(0xFFE53935),
    onError = whiteTextLight
)

@Composable
fun UrbanizaTTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}