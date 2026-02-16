package com.example.urbanizat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbanizat.R
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.components.forms.InputCheckbox
import com.example.urbanizat.ui.components.forms.InputCustom
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ButtonColorsBase
import com.example.urbanizat.utils.LinkStyles
import com.example.urbanizat.utils.ROUNDED_CORNERS
import com.example.urbanizat.utils.SPACER_HEIGHT
import com.example.urbanizat.utils.SPACE_INPUTS_FORM

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.urbanizate),
                        contentDescription = "Logo of UrbanizaT",
                        modifier = Modifier
                            .size(175.dp)
                            .aspectRatio(16f / 9f)
                            .fillMaxSize()
                    )
                }
                Text(
                    text = "Crear una cuenta",
                    modifier = Modifier.weight(0.5f),
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(ROUNDED_CORNERS)
                )
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SPACE_INPUTS_FORM)
        )
        {
            InputCustom(
                value = "Manolo", //TODO: add viewModel logic
                onValueChange = {}, //TODO: add viewModel logic
                label = "Nombre y apellidos",
                placeholder = { Text(text = "Ejemplo: Manolo López") },
                isError = false, //TODO: add viewModel logic
                supportingText = null, //TODO: add viewModel logic
                keyboardType = KeyboardType.Text
            )
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
                value = "", //TODO: add viewModel logic
                onValueChange = {}, //TODO: add viewModel logic
                label = "Contraseña",
                placeholder = { Text(text = "Ejemplo: Contenga letras, números") },
                isError = false, //TODO: add viewModel logic
                supportingText = null, //TODO: add viewModel logic
                isPassword = true,
                passwordVisible = true,
                onTogglePasswordVisibility = {},
                keyboardType = KeyboardType.Password
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
            InputCheckbox(
                modifier = Modifier.fillMaxWidth(0.9f),
                checked = false,
                onCheckedChange = {}
            ) {
                Text(
                    buildAnnotatedString {
                        append("Aceptas los ")
                        withLink(
                            LinkAnnotation.Clickable(
                                tag = "terminos y condiciones",
                                styles = LinkStyles.primary()
                            ){
                                //TODO: añadir link de navegación
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
                            tag = "loggin",
                            styles = LinkStyles.primary()
                        ){
                            //TODO: añadir link de navegación
                        }
                    ) {
                        append("iniciar sesión")
                    }
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            BaseButton(
                modifier = Modifier.padding(top = 4.dp),
                onClick = {},
                colors = ButtonColorsBase.primary(),
                label = "Crear Usuario"
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun RegisterScreenPreview() {
    UrbanizaTTheme {
        RegisterScreen()
    }
}