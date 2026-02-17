package com.example.urbanizat.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = lightBlue,
    onPrimary = background,
    secondary = darkBlue,
    onSecondary = whiteTextLight,
    background = background,
    onBackground = grayText,
    surfaceVariant = green,
    onSurfaceVariant = whiteTextLight,
    error = orange,
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