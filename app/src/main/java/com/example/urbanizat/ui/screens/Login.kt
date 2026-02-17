package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.components.forms.InputCustom
import com.example.urbanizat.ui.components.layouts.FormBody
import com.example.urbanizat.ui.components.layouts.FormHeader
import com.example.urbanizat.ui.components.layouts.MainColumn
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ButtonColorsBase
import com.example.urbanizat.utils.LinkStyles
import com.example.urbanizat.utils.SPACER_HEIGHT

@Composable
fun LoginScreen(){
    MainColumn{
        FormHeader(
            label = "Iniciar sesión"
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        FormBody{
            InputCustom(
                value = "manolo@gmail.com", //TODO: add viewModel logic
                onValueChange = {}, //TODO: add viewModel logic
                label = "E-mail",
                placeholder = { Text(text = "Ejemplo: Manolo López") },
                isError = false, //TODO: add viewModel logic
                supportingText = null, //TODO: add viewModel logic
                keyboardType = KeyboardType.Email
            )
            InputCustom(
                value = "COD_3565458A", //TODO: add viewModel logic
                onValueChange = {}, //TODO: add viewModel logic
                label = "Código de comunidad",
                placeholder = { Text(text = "Ejemplo: COM_156324A") },
                isError = false, //TODO: add viewModel logic
                supportingText = null, //TODO: add viewModel logic
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
                            //TODO: añadir link de navegación
                        }
                    ) {
                        append("crear usuario")
                    }
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            BaseButton(
                modifier = Modifier.padding(top = 4.dp),
                onClick = {},
                colors = ButtonColorsBase.primary(),
                label = "Iniciar sesión"
            )
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview(){
    UrbanizaTTheme {
        LoginScreen()
    }
}