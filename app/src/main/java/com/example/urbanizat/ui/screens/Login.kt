package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.components.forms.InputCustom
import com.example.urbanizat.ui.components.layouts.FormBody
import com.example.urbanizat.ui.components.layouts.FormHeader
import com.example.urbanizat.ui.components.layouts.MainColumn
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.AuthState
import com.example.urbanizat.ui.viewmodels.AuthViewModel
import com.example.urbanizat.utils.ButtonColorsBase
import com.example.urbanizat.utils.LinkStyles
import com.example.urbanizat.utils.SPACER_HEIGHT

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    // Estado local de los campos
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observar estado del ViewModel
    val authState by authViewModel.authState.collectAsState()

    // Navegar al Home cuando el login sea exitoso
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            authViewModel.resetState()
            onNavigateToHome()
        }
    }

    MainColumn {
        FormHeader(label = "Iniciar sesión")
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        FormBody {
            InputCustom(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
                placeholder = { Text(text = "Ejemplo: manolo@gmail.com") },
                isError = authState is AuthState.Error,
                supportingText = null,
                keyboardType = KeyboardType.Email
            )
            InputCustom(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                placeholder = { Text(text = "Tu contraseña") },
                isError = authState is AuthState.Error,
                supportingText = if (authState is AuthState.Error) {
                    {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                isPassword = true,
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
                keyboardType = KeyboardType.Password
            )

            Text(
                buildAnnotatedString {
                    append("No tengo cuenta, ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "register",
                            styles = LinkStyles.primary()
                        ) {
                            onNavigateToRegister()
                        }
                    ) {
                        append("crear usuario")
                    }
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            // Botón o spinner según estado
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                BaseButton(
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {
                        authViewModel.login(email, password)
                    },
                    colors = ButtonColorsBase.primary(),
                    label = "Iniciar sesión",
                    enable = email.isNotBlank() && password.isNotBlank()
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    UrbanizaTTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onNavigateToHome = {}
        )
    }
}