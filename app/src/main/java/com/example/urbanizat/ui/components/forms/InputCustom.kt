package com.example.urbanizat.ui.components.forms


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ROUNDED_CORNERS

@Composable
fun InputCustom(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    keyboardType: KeyboardType,
    onTogglePasswordVisibility: (() -> Unit)? = null
) {
    Column{
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
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            maxLines = maxLines,
            colors = TextFieldDefaults.colors(
                // === ESTADO NORMAL (NO ERROR) ===
                // Fondo del input (estado normal)
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,

                // Texto dentro del input
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,

                // Cursor
                cursorColor = MaterialTheme.colorScheme.primary,

                //Icon
                focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,

                // === ESTADO DE ERROR ===
                errorTextColor = MaterialTheme.colorScheme.onError,
                errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                errorLabelColor = MaterialTheme.colorScheme.onError,
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error
            ),
            trailingIcon = if (isPassword && onTogglePasswordVisibility != null) {
                {
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = if (passwordVisible)
                                "Ocultar contraseña"
                            else
                                "Mostrar contraseña"
                        )
                    }
                }
            } else null,
            shape = RoundedCornerShape(ROUNDED_CORNERS),
            modifier = Modifier.fillMaxWidth(0.9f),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun InputCustomPreview() {
    UrbanizaTTheme {

        var visible by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        InputCustom(
            value = text,
            onValueChange = { text = it},
            label = "Introduce tu nombre",
            placeholder = { Text(text = "placeholder") },
            isError = false,
            supportingText = null,
            isPassword = true,
            passwordVisible = visible,
            keyboardType = KeyboardType.Password,
            onTogglePasswordVisibility = { visible = !visible }
        )
    }
}