package com.example.urbanizat.utils

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

object ButtonColorsBase {
    @Composable
    fun primary() = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary
    )
    @Composable
    fun secondary() = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
}

object LinkStyles {
    @Composable
    fun primary(): TextLinkStyles = TextLinkStyles(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium
        ),
        focusedStyle = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            fontWeight = FontWeight.Medium
        ),
        hoveredStyle = SpanStyle(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium
        ),
        pressedStyle = SpanStyle(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium
        )
    )
}