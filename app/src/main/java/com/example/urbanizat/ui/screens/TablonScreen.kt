package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.NotificacionData
import com.example.urbanizat.ui.viewmodels.TablonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablonScreen(
    onNavigateBack: () -> Unit,
    tablonViewModel: TablonViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var titulo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val anuncios by tablonViewModel.anuncios.collectAsState()
    val isLoading by tablonViewModel.isLoading.collectAsState()
    val error by tablonViewModel.error.collectAsState()
    val esPresidente by tablonViewModel.esPresidente.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Tablón de anuncios", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo anuncio", tint = MaterialTheme.colorScheme.onPrimary)
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
            anuncios.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text(text = "No hay anuncios publicados", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(anuncios) { anuncio ->
                        AnuncioCard(anuncio = anuncio)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                titulo = ""
                mensaje = ""
            },
            title = { Text(text = "Nuevo anuncio", fontWeight = FontWeight.Bold) },
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
                        value = mensaje,
                        onValueChange = { mensaje = it },
                        label = { Text("Mensaje") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (titulo.isNotBlank() && mensaje.isNotBlank()) {
                            tablonViewModel.createAnuncio(titulo, mensaje) {
                                showDialog = false
                                titulo = ""
                                mensaje = ""
                            }
                        }
                    },
                    enabled = titulo.isNotBlank() && mensaje.isNotBlank()
                ) { Text("Publicar") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    titulo = ""
                    mensaje = ""
                }) { Text("Cancelar") }
            },
            containerColor = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
private fun AnuncioCard(anuncio: NotificacionData) {
    val fecha = anuncio.created_at.take(10)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = anuncio.title, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = fecha, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = anuncio.message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TablonScreenPreview() {
    UrbanizaTTheme {
        TablonScreen(onNavigateBack = {})
    }
}
