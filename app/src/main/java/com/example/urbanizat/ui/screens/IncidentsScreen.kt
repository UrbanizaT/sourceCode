package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.Incidencia
import com.example.urbanizat.ui.viewmodels.IncidentState
import com.example.urbanizat.ui.viewmodels.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentsScreen(
    onNavigateToCreateIncident: () -> Unit,
    onNavigateBack: () -> Unit,
    incidentViewModel: IncidentViewModel = viewModel()
) {
    val incidencias by incidentViewModel.incidencias.collectAsState()
    val state by incidentViewModel.state.collectAsState()
    val esPresidente by incidentViewModel.esPresidente.collectAsState()

    LaunchedEffect(Unit) {
        incidentViewModel.loadIncidencias()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Incidencias",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateIncident,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva incidencia",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        when {
            state is IncidentState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state is IncidentState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (state as IncidentState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            incidencias.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No hay incidencias registradas",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(incidencias) { incidencia ->
                        IncidentCard(
                            incidencia = incidencia,
                            esPresidente = esPresidente,
                            onUpdateEstado = { nuevoEstado ->
                                incidentViewModel.updateEstado(incidencia.id, nuevoEstado)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IncidentCard(
    incidencia: Incidencia,
    esPresidente: Boolean = false,
    onUpdateEstado: (String) -> Unit = {}
) {
    val estadoColor = when (incidencia.estado) {
        "pendiente" -> MaterialTheme.colorScheme.error
        "en_proceso" -> MaterialTheme.colorScheme.tertiary
        "resuelta" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val estadoLabel = when (incidencia.estado) {
        "pendiente" -> "Pendiente"
        "en_proceso" -> "En proceso"
        "resuelta" -> "Resuelta"
        else -> incidencia.estado ?: "Pendiente"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = incidencia.type,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = estadoColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = estadoLabel,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = estadoColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📍 ${incidencia.lugar}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = incidencia.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (esPresidente) {
                Spacer(modifier = Modifier.height(10.dp))
                val estados = listOf("pendiente", "en_proceso", "resuelta")
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    estados.forEach { estado ->
                        val seleccionado = incidencia.estado == estado
                        val color = when (estado) {
                            "pendiente" -> MaterialTheme.colorScheme.error
                            "en_proceso" -> MaterialTheme.colorScheme.tertiary
                            else -> MaterialTheme.colorScheme.primary
                        }
                        val label = when (estado) {
                            "pendiente" -> "Pendiente"
                            "en_proceso" -> "En proceso"
                            else -> "Resuelta"
                        }
                        FilterChip(
                            selected = seleccionado,
                            onClick = { if (!seleccionado) onUpdateEstado(estado) },
                            label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = color.copy(alpha = 0.2f),
                                selectedLabelColor = color
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncidentsScreenPreview() {
    UrbanizaTTheme {
        IncidentsScreen(
            onNavigateToCreateIncident = {},
            onNavigateBack = {}
        )
    }
}