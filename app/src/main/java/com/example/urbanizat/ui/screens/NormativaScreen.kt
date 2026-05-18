package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.NormativaData
import com.example.urbanizat.ui.viewmodels.NormativaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormativaScreen(
    onNavigateBack: () -> Unit,
    normativaViewModel: NormativaViewModel = viewModel()
) {
    var expandedId by remember { mutableStateOf<Long?>(null) }
    var mostrarDialogoCrear by remember { mutableStateOf(false) }
    var articuloAEditar by remember { mutableStateOf<NormativaData?>(null) }
    var articuloAEliminar by remember { mutableStateOf<NormativaData?>(null) }

    val normativas by normativaViewModel.normativas.collectAsState()
    val isLoading by normativaViewModel.isLoading.collectAsState()
    val error by normativaViewModel.error.collectAsState()
    val esPresidente by normativaViewModel.esPresidente.collectAsState()

    if (mostrarDialogoCrear) {
        DialogNormativa(
            titulo = "",
            cuerpo = "",
            onDismiss = { mostrarDialogoCrear = false },
            onConfirm = { titulo, cuerpo ->
                normativaViewModel.createNormativa(titulo, cuerpo) {
                    mostrarDialogoCrear = false
                }
            }
        )
    }

    articuloAEditar?.let { articulo ->
        DialogNormativa(
            titulo = articulo.title,
            cuerpo = articulo.body,
            onDismiss = { articuloAEditar = null },
            onConfirm = { titulo, cuerpo ->
                normativaViewModel.updateNormativa(articulo.id, titulo, cuerpo) {
                    articuloAEditar = null
                }
            }
        )
    }

    articuloAEliminar?.let { articulo ->
        AlertDialog(
            onDismissRequest = { articuloAEliminar = null },
            title = { Text("Eliminar artículo", fontWeight = FontWeight.Bold) },
            text = { Text("¿Seguro que quieres eliminar \"${articulo.title}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    normativaViewModel.deleteNormativa(articulo.id) {
                        articuloAEliminar = null
                    }
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { articuloAEliminar = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Normativa", fontWeight = FontWeight.Bold) },
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
                    onClick = { mostrarDialogoCrear = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir artículo",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Article,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Reglamento interno de la comunidad. Pulsa cada artículo para ver su contenido.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            when {
                isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator() }
                    }
                }
                error != null -> {
                    item {
                        Text(
                            text = "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                normativas.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay normativa publicada",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                else -> {
                    items(normativas) { articulo ->
                        ArticuloCard(
                            articulo = articulo,
                            isExpanded = expandedId == articulo.id,
                            esPresidente = esPresidente,
                            onClick = {
                                expandedId = if (expandedId == articulo.id) null else articulo.id
                            },
                            onEdit = { articuloAEditar = articulo },
                            onDelete = { articuloAEliminar = articulo }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticuloCard(
    articulo: NormativaData,
    isExpanded: Boolean,
    esPresidente: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = articulo.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                if (esPresidente) {
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Text(
                    text = if (isExpanded) "▲" else "▼",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = articulo.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DialogNormativa(
    titulo: String,
    cuerpo: String,
    onDismiss: () -> Unit,
    onConfirm: (titulo: String, cuerpo: String) -> Unit
) {
    var tituloState by remember { mutableStateOf(titulo) }
    var cuerpoState by remember { mutableStateOf(cuerpo) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (titulo.isBlank()) "Nuevo artículo" else "Editar artículo", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = tituloState,
                    onValueChange = { tituloState = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cuerpoState,
                    onValueChange = { cuerpoState = it },
                    label = { Text("Contenido") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (tituloState.isNotBlank() && cuerpoState.isNotBlank()) onConfirm(tituloState, cuerpoState) },
                enabled = tituloState.isNotBlank() && cuerpoState.isNotBlank()
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NormativaScreenPreview() {
    UrbanizaTTheme {
        NormativaScreen(onNavigateBack = {})
    }
}
