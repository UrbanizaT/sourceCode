package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.components.forms.InputCustom
import com.example.urbanizat.ui.components.layouts.FormBody
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.ui.viewmodels.IncidentState
import com.example.urbanizat.ui.viewmodels.IncidentViewModel
import com.example.urbanizat.utils.ButtonColorsBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIncidentScreen(
    onNavigateBack: () -> Unit,
    incidentViewModel: IncidentViewModel = viewModel()
) {
    var tipo by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val state by incidentViewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is IncidentState.Success) {
            incidentViewModel.resetState()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nueva incidencia",
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
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
        FormBody(modifier = Modifier.padding(top = 24.dp)) {
            InputCustom(
                value = tipo,
                onValueChange = { tipo = it },
                label = "Tipo de incidencia",
                placeholder = { Text("Ejemplo: Fontanería, Electricidad...") },
                isError = false,
                supportingText = null,
                keyboardType = KeyboardType.Text
            )
            InputCustom(
                value = lugar,
                onValueChange = { lugar = it },
                label = "Lugar",
                placeholder = { Text("Ejemplo: Escalera A, Garaje...") },
                isError = false,
                supportingText = null,
                keyboardType = KeyboardType.Text
            )
            InputCustom(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción",
                placeholder = { Text("Describe el problema con detalle") },
                isError = state is IncidentState.Error,
                supportingText = if (state is IncidentState.Error) {
                    { Text(text = (state as IncidentState.Error).message,
                        color = MaterialTheme.colorScheme.error) }
                } else null,
                keyboardType = KeyboardType.Text,
                maxLines = 4
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (state is IncidentState.Loading) {
                CircularProgressIndicator()
            } else {
                BaseButton(
                    onClick = {
                        incidentViewModel.createIncidencia(
                            tipo = tipo,
                            lugar = lugar,
                            descripcion = descripcion,
                            onSuccess = onNavigateBack
                        )
                    },
                    colors = ButtonColorsBase.primary(),
                    label = "Crear incidencia",
                    enable = tipo.isNotBlank() && lugar.isNotBlank() && descripcion.isNotBlank()
                )
            }
        }
        } // Box
    }
}

@Preview(showBackground = true)
@Composable
fun CreateIncidentScreenPreview() {
    UrbanizaTTheme {
        CreateIncidentScreen(onNavigateBack = {})
    }
}