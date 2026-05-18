package com.example.urbanizat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.EstadisticaData
import com.example.urbanizat.ui.viewmodels.FinanzasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanzasScreen(
    onNavigateBack: () -> Unit,
    finanzasViewModel: FinanzasViewModel = viewModel()
) {
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    val filtros = listOf("Todos", "Ingresos", "Gastos")
    var mostrarDialogo by remember { mutableStateOf(false) }

    val movimientos by finanzasViewModel.movimientos.collectAsState()
    val isLoading by finanzasViewModel.isLoading.collectAsState()
    val error by finanzasViewModel.error.collectAsState()
    val esPresidente by finanzasViewModel.esPresidente.collectAsState()

    val totalIngresos = movimientos.filter { it.value > 0 }.sumOf { it.value }
    val totalGastos = movimientos.filter { it.value < 0 }.sumOf { -it.value }
    val balance = totalIngresos - totalGastos

    val movimientosFiltrados = when (filtroSeleccionado) {
        "Ingresos" -> movimientos.filter { it.value > 0 }
        "Gastos" -> movimientos.filter { it.value < 0 }
        else -> movimientos
    }

    if (mostrarDialogo) {
        DialogCrearMovimiento(
            onDismiss = { mostrarDialogo = false },
            onCreate = { concepto, valor ->
                finanzasViewModel.createMovimiento(concepto, valor) {
                    mostrarDialogo = false
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Finanzas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            if (esPresidente) {
                FloatingActionButton(
                    onClick = { mostrarDialogo = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir movimiento", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { BalanceCard(balance = balance, totalIngresos = totalIngresos, totalGastos = totalGastos) }
                    item { BarraProgreso(totalIngresos = totalIngresos, totalGastos = totalGastos) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            filtros.forEach { filtro ->
                                FilterChip(
                                    selected = filtroSeleccionado == filtro,
                                    onClick = { filtroSeleccionado = filtro },
                                    label = { Text(filtro) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }
                    item {
                        Text(
                            text = "Movimientos",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    if (movimientosFiltrados.isEmpty()) {

                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                Text(text = "No hay movimientos", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    } else {
                        items(movimientosFiltrados) { movimiento ->
                            MovimientoCard(
                                movimiento = movimiento,
                                esPresidente = esPresidente,
                                onDelete = { finanzasViewModel.deleteMovimiento(movimiento.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceCard(balance: Double, totalIngresos: Double, totalGastos: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Balance actual", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${String.format("%.2f", balance)} €",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null, tint = Color(0xFF81C784), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Ingresos", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                    Text(text = "${String.format("%.2f", totalIngresos)} €", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = Color(0xFF81C784))
                }
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null, tint = Color(0xFFEF9A9A), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Gastos", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                    Text(text = "${String.format("%.2f", totalGastos)} €", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = Color(0xFFEF9A9A))
                }
            }
        }
    }
}

@Composable
private fun BarraProgreso(totalIngresos: Double, totalGastos: Double) {
    val total = totalIngresos + totalGastos
    val porcentajeIngresos = if (total > 0) (totalIngresos / total).toFloat() else 0f
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Ingresos vs Gastos", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                Text(text = "${(porcentajeIngresos * 100).toInt()}% ingresos", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)).background(Color(0xFFEF9A9A))) {
                Box(modifier = Modifier.fillMaxWidth(porcentajeIngresos).fillMaxHeight().clip(RoundedCornerShape(6.dp)).background(Color(0xFF4CAF50)))
            }
        }
    }
}

@Composable
private fun MovimientoCard(
    movimiento: EstadisticaData,
    esPresidente: Boolean = false,
    onDelete: () -> Unit = {}
) {
    val esIngreso = movimiento.value > 0
    val color = if (esIngreso) Color(0xFF4CAF50) else Color(0xFFE53935)
    val signo = if (esIngreso) "+" else ""
    val icono = if (esIngreso) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
    val fecha = try {
        val parts = movimiento.month.take(10).split("-")
        "${parts[2]}/${parts[1]}/${parts[0]}"
    } catch (e: Exception) { movimiento.month.take(10) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icono, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = movimiento.concept, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Text(text = fecha, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                text = "$signo${String.format("%.2f", movimiento.value)} €",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = color
            )
            if (esPresidente) {
                IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogCrearMovimiento(
    onDismiss: () -> Unit,
    onCreate: (concepto: String, valor: Double) -> Unit
) {
    var concepto by remember { mutableStateOf("") }
    var valorStr by remember { mutableStateOf("") }
    var esIngreso by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo movimiento", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = concepto,
                    onValueChange = { concepto = it },
                    label = { Text("Concepto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valorStr,
                    onValueChange = { valorStr = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Importe (€)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = esIngreso, onClick = { esIngreso = true }, label = { Text("Ingreso") })
                    FilterChip(selected = !esIngreso, onClick = { esIngreso = false }, label = { Text("Gasto") })
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val valor = valorStr.toDoubleOrNull() ?: return@TextButton
                    onCreate(concepto, if (esIngreso) valor else -valor)
                },
                enabled = concepto.isNotBlank() && valorStr.toDoubleOrNull() != null
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Preview(showBackground = true)
@Composable
fun FinanzasScreenPreview() {
    UrbanizaTTheme {
        FinanzasScreen(onNavigateBack = {})
    }
}
