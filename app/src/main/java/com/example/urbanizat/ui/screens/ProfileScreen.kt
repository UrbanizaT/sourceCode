package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.outlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top app bar
        androidx.compose.material3.TopAppBar(
            title = { Text("Mi Perfil") },
            navigationIcon = {
                androidx.compose.material3.IconButton(onClick = { /* Navigate back */ }) {
                    androidx.compose.material3.Icon(
                        androidx.compose.material3.icons.default.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { /* Edit profile */ }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar perfil")
                }
            }
        )

        // Main content
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Profile header
            ProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))

            // Profile details
            ProfileDetailsSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Account settings
            AccountSettingsSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Logout button
            BaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Handle logout */ },
                colors = androidx.compose.urbanizat.utils.ButtonColorsBase.primary(),
                label = "Cerrar Sesión"
            )
        }
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
    ) {
        // Avatar
        androidx.compose.foundation.layout.Circle(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                        .copy(alpha = 0.2f)
                )
        ) {
            Text(
                text = "AT", // Initials from Alejandro Trujillo (sample user)
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        // User info
        androidx.compose.foundation.layout.Column {
            Text(
                text = "Alejandro Trujillo",
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Vecino • Apartamento 3B",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Código de Comunidad: COM_156324A",
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProfileDetailsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.material3.MaterialTheme.cardShape
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SectionTitle(title = "Información Personal")

            ProfileInfoRow(
                label = "Nombre completo",
                value = "Alejandro Trujillo Prokhorenko"
            )
            Divider()
            ProfileInfoRow(
                label = "Correo electrónico",
                value = "alejandro.trujillo@email.com"
            )
            Divider()
            ProfileInfoRow(
                label = "Teléfono de contacto",
                value = "+34 600 123 456"
            )
            Divider()
            ProfileInfoRow(
                label = "Fecha de nacimiento",
                value = "15 de marzo de 1990"
            )
        }
    }
}

@Composable
fun AccountSettingsSection() {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionTitle(title = "Configuración de cuenta")

        SettingsOption(
            title = "Notificaciones",
            description = "Recibir alertas por push y email",
            icon = androidx.compose.material3.icons.default.Notifications,
            onClick = { /* Toggle notifications */ }
        )
        Divider()
        SettingsOption(
            title = "Cambiar contraseña",
            description = "Actualizar tu contraseña de acceso",
            icon = androidx.compose.material3.icons.default.Password,
            onClick = { /* Navigate to change password */ }
        )
        Divider()
        SettingsOption(
            title = "Datos de facturación",
            description = "Gestionar información de pagos y recibos",
            icon = androidx.compose.material3.icons.default.Receipt,
            onClick = { /* Navigate to billing */ }
        )
        Divider()
        SettingsOption(
            title = "Privacidad y seguridad",
            description = "Revisar permisos y datos personales",
            icon = androidx.compose.material3.icons.default.Shield,
            onClick = { /* Navigate to privacy */ }
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(120.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        // Edit icon (optional)
        androidx.compose.material3.IconButton(
            onClick = { /* Edit field */ }
        ) {
            androidx.compose.material3.Icon(
                androidx.compose.material3.icons.default.Edit,
                contentDescription = "Editar $label"
            )
        }
    }
}

@Composable
fun SettingsOption(
    title: String,
    description: String,
    icon: androidx.compose.material3.IconVector,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.material3.MaterialTheme.cardShape
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column {
                Text(
                    text = title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            androidx.compose.material3.Icon(
                androidx.compose.material3.icons.default.ChevronRight,
                contentDescription = "Siguiente",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun Divider() {
    androidx.compose.foundation.layout.Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    UrbanizaTTheme {
        ProfileScreen()
    }
}