package com.example.urbanizat.ui.components.forms

import android.R
import android.graphics.Color.alpha
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ROUNDED_CORNERS

@Composable
fun InputCustom(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)? = null,
) {
    Column() {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSecondary
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            isError = isError,
            supportingText = supportingText,
            colors = TextFieldDefaults.colors(
                // === ESTADO NORMAL (NO ERROR) ===
                // Fondo del input (estado normal)
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,

                // Texto dentro del input
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

                // Cursor
                cursorColor = MaterialTheme.colorScheme.primary,

                // === ESTADO DE ERROR ===
                errorTextColor = MaterialTheme.colorScheme.onError,
                errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                errorLabelColor = MaterialTheme.colorScheme.onError,
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(ROUNDED_CORNERS),
        )
    }
}

@Preview(showBackground = false)
@Composable
fun InputCustomPreview() {
    UrbanizaTTheme {
        InputCustom(
            value = "text",
            onValueChange = {},
            label = "Introduce tu nombre",
            placeholder = { Text(text = "placeholder") },
            isError = false,
            supportingText = null
        )
    }
}