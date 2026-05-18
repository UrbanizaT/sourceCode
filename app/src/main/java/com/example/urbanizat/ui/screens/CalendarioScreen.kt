package com.example.urbanizat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.CalendarioViewModel
import com.example.urbanizat.ui.viewmodels.EventoSupabase
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(
    onNavigateBack: () -> Unit,
    calendarioViewModel: CalendarioViewModel = viewModel()
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val eventos by calendarioViewModel.eventos.collectAsState()
    val isLoading by calendarioViewModel.isLoading.collectAsState()
    val error by calendarioViewModel.error.collectAsState()
    val esPresidente by calendarioViewModel.esPresidente.collectAsState()

    // Parsear fechas de los eventos
    val formatter = DateTimeFormatter.ISO_DATE

    val eventosDiaSeleccionado = eventos.filter { evento ->
        try {
            val fecha = LocalDate.parse(evento.start_date.take(10), formatter)
            fecha == selectedDate
        } catch (e: Exception) { false }
    }

    val diasConEvento = eventos
        .filter { evento ->
            try {
                val fecha = LocalDate.parse(evento.start_date.take(10), formatter)
                YearMonth.from(fecha) == currentMonth
            } catch (e: Exception) { false }
        }
        .mapNotNull { evento ->
            try {
                LocalDate.parse(evento.start_date.take(10), formatter).dayOfMonth
            } catch (e: Exception) { null }
        }
        .toSet()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendario", fontWeight = FontWeight.Bold) },
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
            if (esPresidente) {
                FloatingActionButton(
                    onClick = { mostrarDialogo = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Crear evento",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        if (mostrarDialogo) {
            DialogCrearEvento(
                fechaInicial = selectedDate.toString(),
                onDismiss = { mostrarDialogo = false },
                onCreate = { titulo, descripcion, fecha, tipo ->
                    calendarioViewModel.createEvento(titulo, descripcion, fecha, tipo) {
                        mostrarDialogo = false
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            CalendarioMensual(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                diasConEvento = diasConEvento,
                onDaySelected = { selectedDate = it },
                onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
                onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Eventos del ${selectedDate.dayOfMonth} de ${
                    selectedDate.month.getDisplayName(TextStyle.FULL, Locale("es"))
                }",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                eventosDiaSeleccionado.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay eventos este día",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(eventosDiaSeleccionado) { evento ->
                            EventoCardSupabase(
                                evento = evento,
                                esPresidente = esPresidente,
                                onDelete = { calendarioViewModel.deleteEvento(evento.id) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Leyenda()
        }
    }
}

@Composable
private fun EventoCardSupabase(evento: EventoSupabase, esPresidente: Boolean = false, onDelete: () -> Unit = {}) {
    val colorEvento = when (evento.tipo) {
        "mantenimiento" -> Color(0xFFF4A261)
        "recordatorio" -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(colorEvento)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = evento.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = evento.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (esPresidente) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar evento",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarioMensual(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    diasConEvento: Set<Int>,
    onDaySelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousMonth) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                        contentDescription = "Mes anterior",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale("es"))
                        .replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onNextMonth) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                        contentDescription = "Mes siguiente",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val diasSemana = listOf("L", "M", "X", "J", "V", "S", "D")
            Row(modifier = Modifier.fillMaxWidth()) {
                diasSemana.forEach { dia ->
                    Text(
                        text = dia,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            val firstDayOfMonth = currentMonth.atDay(1)
            val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.value - 1)
            val daysInMonth = currentMonth.lengthOfMonth()
            val totalCells = firstDayOfWeek + daysInMonth
            val rows = (totalCells + 6) / 7

            for (row in 0 until rows) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        val dayIndex = row * 7 + col - firstDayOfWeek + 1
                        if (dayIndex < 1 || dayIndex > daysInMonth) {
                            Box(modifier = Modifier.weight(1f))
                        } else {
                            val date = currentMonth.atDay(dayIndex)
                            val isSelected = date == selectedDate
                            val isToday = date == LocalDate.now()
                            val hasEvent = dayIndex in diasConEvento

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                            else -> Color.Transparent
                                        }
                                    )
                                    .clickable { onDaySelected(date) },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = dayIndex.toString(),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = when {
                                            isSelected -> MaterialTheme.colorScheme.onPrimary
                                            isToday -> MaterialTheme.colorScheme.primary
                                            else -> MaterialTheme.colorScheme.onSurface
                                        },
                                        fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                    if (hasEvent && !isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(4.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primary)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Leyenda() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LeyendaItem(color = Color(0xFFF4A261), label = "Mantenimiento")
        LeyendaItem(color = MaterialTheme.colorScheme.primary, label = "Reunión")
        LeyendaItem(color = Color(0xFF4CAF50), label = "Recordatorio")
    }
}

@Composable
private fun LeyendaItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogCrearEvento(
    fechaInicial: String,
    onDismiss: () -> Unit,
    onCreate: (titulo: String, descripcion: String, fecha: String, tipo: String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(fechaInicial) }
    var tipo by remember { mutableStateOf("reunion") }

    val tipos = listOf(
        "reunion" to "Reunión",
        "mantenimiento" to "Mantenimiento",
        "recordatorio" to "Recordatorio"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo evento", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha (YYYY-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                var expandedTipo by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedTipo,
                    onExpandedChange = { expandedTipo = !expandedTipo }
                ) {
                    OutlinedTextField(
                        value = tipos.first { it.first == tipo }.second,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTipo,
                        onDismissRequest = { expandedTipo = false }
                    ) {
                        tipos.forEach { (valor, etiqueta) ->
                            DropdownMenuItem(
                                text = { Text(etiqueta) },
                                onClick = {
                                    tipo = valor
                                    expandedTipo = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (titulo.isNotBlank() && fecha.isNotBlank()) onCreate(titulo, descripcion, fecha, tipo) },
                enabled = titulo.isNotBlank() && fecha.isNotBlank()
            ) { Text("Crear") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarioScreenPreview() {
    UrbanizaTTheme {
        CalendarioScreen(onNavigateBack = {})
    }
}