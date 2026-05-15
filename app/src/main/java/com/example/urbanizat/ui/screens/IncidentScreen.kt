package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Toast
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.filledTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.icons.Icons
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun IncidentScreen() {
    Column {
        // Top app bar
        androidx.compose.material3.TopAppBar(
            title = { Text("Reportar Incidencia") },
            navigationIcon = {
                androidx.compose.material3.IconButton(onClick = { /* Navigate back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Form content
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        ) {
            // Description field
            Text("Descripción de la incidencia", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Describe lo que aconteceu") },
                placeholder = { Text("Ejemplo: Luz escalera del tercer piso parpadea") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Category
            Text("Categoría", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Selecciona categoría") },
                placeholder = { Text("Ejemplo: Electricidad, Fontanería, Seguridad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Text("Ubicación", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("¿Dónde ocurrió?") },
                placeholder = { Text("Ejemplo: Pasillo del bloque B, puerta 3B") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Priority
            Text("Prioridad", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                PriorityButton(text = "Baja", isSelected = false, onClick = {})
                PriorityButton(text = "Media", isSelected = true, onClick = {})
                PriorityButton(text = "Alta", isSelected = false, onClick = {})
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Submit button
            BaseButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /* Submit incident */ },
                colors = androidx.compose.urbanizat.utils.ButtonColorsBase.primary(),
                label = "Reportar Incidencia"
            )
        }
    }
}

@Composable
fun PriorityButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = Modifier.width(80.dp),
        colors = if (isSelected) {
            androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    ) {
        Text(text)
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun IncidentScreenPreview() {
    UrbanizaTTheme {
        IncidentScreen()
    }
}