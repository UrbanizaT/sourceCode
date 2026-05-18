package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Política de privacidad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Política de Privacidad y RGPD",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            SeccionRGPD(
                titulo = "1. Responsable del tratamiento",
                contenido = "La comunidad de vecinos gestionada a través de UrbanizaT es la responsable del tratamiento de los datos personales recogidos a través de esta aplicación."
            )

            SeccionRGPD(
                titulo = "2. Datos que recopilamos",
                contenido = "Recopilamos los siguientes datos personales:\n• Nombre y apellidos\n• Dirección de correo electrónico\n• Información de la comunidad a la que pertenece\n\nEstos datos son necesarios para el funcionamiento del servicio."
            )

            SeccionRGPD(
                titulo = "3. Finalidad del tratamiento",
                contenido = "Los datos personales se utilizan exclusivamente para:\n• Gestionar su cuenta de usuario\n• Facilitar la comunicación entre vecinos\n• Gestionar incidencias, eventos y normativa de la comunidad\n• Gestionar las finanzas comunitarias"
            )

            SeccionRGPD(
                titulo = "4. Base legal",
                contenido = "El tratamiento de sus datos se basa en el consentimiento expreso que usted otorga al registrarse en la aplicación, de conformidad con el Reglamento (UE) 2016/679 (RGPD)."
            )

            SeccionRGPD(
                titulo = "5. Conservación de datos",
                contenido = "Sus datos se conservarán mientras mantenga una cuenta activa en la aplicación. Podrá solicitar la eliminación de sus datos en cualquier momento contactando con el administrador de su comunidad."
            )

            SeccionRGPD(
                titulo = "6. Sus derechos",
                contenido = "Usted tiene derecho a:\n• Acceder a sus datos personales\n• Rectificar datos incorrectos\n• Solicitar la eliminación de sus datos\n• Oponerse al tratamiento\n• Portabilidad de los datos\n\nPara ejercer estos derechos, contacte con el presidente de su comunidad."
            )

            SeccionRGPD(
                titulo = "7. Seguridad",
                contenido = "Aplicamos medidas técnicas y organizativas para proteger sus datos personales frente a accesos no autorizados, pérdida o alteración. Los datos se almacenan de forma segura en servidores con cifrado en tránsito y en reposo."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Al registrarse en UrbanizaT, acepta esta política de privacidad.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entendido")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SeccionRGPD(titulo: String, contenido: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = contenido,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TermsScreenPreview() {
    UrbanizaTTheme {
        TermsScreen(onNavigateBack = {})
    }
}
