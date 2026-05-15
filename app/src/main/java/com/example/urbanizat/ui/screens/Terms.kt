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
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.components.layouts.FormBody
import com.example.urbanizat.ui.components.layouts.FormHeader
import com.example.urbanizat.ui.components.layouts.MainColumn
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ButtonColorsBase
import com.example.urbanizat.utils.LinkStyles
import com.example.urbanizat.utils.SPACER_HEIGHT

@Composable
fun TermsScreen(
    onNavigateToMainApp: () -> Unit
) {
    MainColumn {
        FormHeader(
            label = "Términos y Condiciones"
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))

        FormBody {
            // Terms content placeholder
            Text(
                text = """
                    TÉRMINOS Y CONDICIONES DE URBANIZAT

                    1. ACEPTACIÓN DE LOS TÉRMINOS
                    Al acceder o utilizar la aplicación UrbanizaT, usted acepta estar sujeto a estos términos y condiciones.

                    2. DESCRIPCIÓN DEL SERVICIO
                    UrbanizaT es una aplicación móvil diseñada para la gestión de comunidades de propietarios.

                    3. OBLIGACIONES DEL USUARIO
                    El usuario se compromete a utilizar la aplicación de conformidad con la ley, la moral, el orden público y estas condiciones.

                    4. PROPIEDAD INTELECTUAL
                    Todos los derechos de propiedad intelectual de la aplicación y su contenido son titularidad de UrbanizaT o de terceros.

                    5. LIMITACIÓN DE RESPONSABILIDAD
                    UrbanizaT no será responsable por daños directos, indirectos, incidentales o consecuentes derivados del uso de la aplicación.

                    6. LEY APLICABLE Y JURISDICCIÓN
                    Estos términos se regirán e interpretarán de acuerdo con las leyes de España.

                    Al marcar la casilla de aceptación, usted declara haber leído, comprendido y aceptado todos los términos y condiciones anteriores.
                """.trimIndent(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            BaseButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = { onNavigateToMainApp() },
                colors = ButtonColorsBase.primary(),
                label = "Aceptar y Continuar"
            )
        }
    }
}

@Preview
@Composable
fun TermsScreenPreview() {
    UrbanizaTTheme {
        TermsScreen(
            onNavigateToMainApp = {}
        )
    }
}