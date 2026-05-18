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
import com.example.urbanizat.ui.components.forms.InputCheckbox
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
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToTerms: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var puerta by remember { mutableStateOf("") }
    var codigoComunidad by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    // Navegar al login cuando el registro sea exitoso
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            authViewModel.resetState()
            onNavigateToLogin()
        }
    }

    MainColumn {
        FormHeader(label = "Crear una cuenta")
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        FormBody {
            InputCustom(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre y apellidos",
                placeholder = { Text(text = "Ejemplo: Manolo López") },
                isError = false,
                supportingText = null,
                keyboardType = KeyboardType.Text
            )
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
                placeholder = { Text(text = "Mínimo 6 caracteres") },
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
            InputCustom(
                value = puerta,
                onValueChange = { puerta = it },
                label = "Puerta / Piso",
                placeholder = { Text(text = "Ejemplo: 3B") },
                isError = false,
                supportingText = null,
                keyboardType = KeyboardType.Text
            )
            InputCustom(
                value = codigoComunidad,
                onValueChange = { codigoComunidad = it },
                label = "Código de comunidad",
                placeholder = { Text(text = "Ejemplo: 156324") },
                isError = authState is AuthState.Error,
                supportingText = null,
                keyboardType = KeyboardType.Number  // numérico, como el campo code en Supabase
            )
            InputCheckbox(
                modifier = Modifier.fillMaxWidth(0.9f),
                checked = aceptaTerminos,
                onCheckedChange = { aceptaTerminos = it }
            ) {
                Text(
                    buildAnnotatedString {
                        append("Aceptas los ")
                        withLink(
                            LinkAnnotation.Clickable(
                                tag = "terminos",
                                styles = LinkStyles.primary()
                            ) {
                                onNavigateToTerms()
                            }
                        ) {
                            append("términos y condiciones")
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                buildAnnotatedString {
                    append("Ya tengo usuario, ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "login",
                            styles = LinkStyles.primary()
                        ) {
                            onNavigateToLogin()
                        }
                    ) {
                        append("iniciar sesión")
                    }
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                BaseButton(
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {
                        authViewModel.register(
                            emailInput = email,
                            passwordInput = password,
                            nombreInput = nombre,
                            puertaInput = puerta,
                            codigoInput = codigoComunidad
                        )
                    },
                    colors = ButtonColorsBase.primary(),
                    label = "Crear Usuario",
                    enable = email.isNotBlank() &&
                            password.isNotBlank() &&
                            nombre.isNotBlank() &&
                            puerta.isNotBlank() &&
                            codigoComunidad.isNotBlank() &&
                            aceptaTerminos
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RegisterScreenPreview() {
    UrbanizaTTheme {
        RegisterScreen(
            onNavigateToLogin = {},
            onNavigateToTerms = {}
        )
    }
}
