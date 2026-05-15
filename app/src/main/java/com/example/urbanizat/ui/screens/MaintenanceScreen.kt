package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.fab
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.default.Add
import androidx.compose.material3.icons.default.CalendarToday
import androidx.compose.material3.icons.default.Edit
import androidx.compose.material3.icons.default.Delete
import androidx.compose.material3.icons.default.Notifications
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
fun MaintenanceScreen() {
    androidx.compose.material3.Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendario de Mantenimiento") },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        fab = {
            FloatingActionButton(
                onClick = { /* Add maintenance event */ },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar evento")
            }
        }
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Filter and view options
            MaintenanceHeader()

            Spacer(modifier = Modifier.height(12.dp))

            // Calendar view (simplified)
            CalendarView()

            Spacer(modifier = Modifier.height(16.dp))

            // Upcoming maintenance list
            Text(
                text = "Próximos mantenimientos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            MaintenanceList(
                maintenances = SampleData.maintenances,
                onMaintenanceClick = { id -> /* Handle maintenance click */ },
                onEditClick = { id -> /* Handle edit */ },
                onDeleteClick = { id -> /* Handle delete */ }
            )
        }
    }
}

@Composable
fun MaintenanceHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
    ) {
        Text(
            text = "Vista:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { /* Toggle view */ },
            modifier = Modifier.width(80.dp),
            colors = MaterialTheme.colorScheme.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("Lista")
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* Show filter */ },
            modifier = Modifier.width(100.dp),
            colors = MaterialTheme.colorScheme.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("Filtrar")
        }
    }
}

@Composable
fun CalendarView() {
    Card(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        shape = MaterialTheme.cardShape
    ) {
        // Simplified calendar view - in a real app, this would use AndroidView with CalendarView
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(
                text = "Calendario",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Vista mensual de mantenimientos programados",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Visual representation of calendar grid
            Row(
                modifier = Modifier.width(200.dp),
                verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
            ) {
                repeat(7) { dayIndex ->
                    Column(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = when (dayIndex) {
                                0 -> "L"
                                1 -> "M"
                                2 -> "X"
                                3 -> "J"
                                4 -> "V"
                                5 -> "S"
                                6 -> "D"
                                else -> ""
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    if (dayIndex == 3) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = MaterialTheme.cardShape
                        )
                    }
                    if (dayIndex < 6) Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
fun MaintenanceList(
    maintenances: List<Maintenance>,
    onMaintenanceClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.padding(vertical = 8.dp)
    ) {
        items(maintenances) { maintenance ->
            MaintenanceCard(
                maintenance = maintenance,
                onMaintenanceClick = { onMaintenanceClick(maintenance.id) },
                onEditClick = { onEditClick(maintenance.id) },
                onDeleteClick = { onDeleteClick(maintenance.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MaintenanceCard(
    maintenance: Maintenance,
    onMaintenanceClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMaintenanceClick() },
        shape = MaterialTheme.cardShape
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
            ) {
                // Icon based on type
                Icon(
                    imageVector = when (maintenance.type) {
                        MaintenanceType.CLEANING -> Icons.Default.LocalFloral
                        MaintenanceType.REPAIRS -> Icons.Default.Build
                        MaintenanceType.INSPECTION -> Icons.Default.SafetyCheck
                        MaintenanceType.GARDENING -> Icons.Default.Grass
                        MaintenanceType.SECURITY -> Icons.Default.Security
                        else -> Icons.Default.Home
                    },
                    contentDescription = "${maintenance.type} icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))

                // Maintenance info
                Column {
                    Text(
                        text = maintenance.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${maintenance.date} • ${maintenance.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Status indicator
                when (maintenance.status) {
                    MaintenanceStatus.SCHEDULED -> {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(MaterialTheme.colorScheme.warning),
                            shape = MaterialTheme.cardShape
                        )
                    }
                    MaintenanceStatus.IN_PROGRESS -> {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(MaterialTheme.colorScheme.primary),
                            shape = MaterialTheme.cardShape
                        )
                    }
                    MaintenanceStatus.COMPLETED -> {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(MaterialTheme.colorScheme.success),
                            shape = MaterialTheme.cardShape
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = maintenance.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
            ) {
                IconButton(
                    onClick = { onEditClick() }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(
                    onClick = { onDeleteClick() }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

// Data classes
enum class MaintenanceType {
    CLEANING, REPAIRS, INSPECTION, GARDENING, SECURITY, OTHER
}

enum class MaintenanceStatus {
    SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
}

data class Maintenance(
    val id: String,
    val title: String,
    val description: String,
    val type: MaintenanceType,
    val date: String,
    val time: String,
    val status: MaintenanceStatus
)

object SampleData {
    val maintenances = listOf(
        Maintenance(
            id = "1",
            title = "Mantenimiento Ascensor Principal",
            description = "Revisión mensual de seguridad y lubricación de mecanismos.",
            type = MaintenanceType.REPAIRS,
            date = "28/05/2024",
            time = "09:00 - 13:00",
            status = MaintenanceStatus.SCHEDULED
        ),
        Maintenance(
            id = "2",
            title = "Limpieza de Zonas Comunes",
            description = "Limpieza profunda de entradas, pasillos y áreas de recreo.",
            type = MaintenanceType.CLEANING,
            date = "29/05/2024",
            time = "08:00 - 12:00",
            status = MaintenanceStatus.SCHEDULED
        ),
        Maintenance(
            id = "3",
            title = "Inspección de Sistemas Contraincendio",
            description = "Verificación de extintores, detectores y rociadores.",
            type = MaintenanceType.INSPECTION,
            date = "30/05/2024",
            time = "10:00 - 12:00",
            status = MaintenanceStatus.IN_PROGRESS
        ),
        Maintenance(
            id = "4",
            title = "Poda de Jardinería",
            description = "Corte de césped, poda de setos y limpieza de macetas.",
            type = MaintenanceType.GARDENING,
            date = "31/05/2024",
            time = "07:00 - 11:00",
            status = MaintenanceStatus.SCHEDULED
        )
    )
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun MaintenanceScreenPreview() {
    UrbanizaTTheme {
        MaintenanceScreen()
    }
}