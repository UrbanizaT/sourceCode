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
import androidx.compose.material3.icons.default.Edit
import androidx.compose.material3.icons.default.Delete
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
fun SurveyScreen() {
    androidx.compose.material3.Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sistema de Encuestas") },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        fab = {
            FloatingActionButton(
                onClick = { /* Create new survey */ },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear encuesta")
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

            // Surveys list
            SurveysList(
                surveys = SampleData.surveys,
                onSurveyClick = { surveyId -> /* Handle survey click */ },
                onTakeSurveyClick = { surveyId -> /* Handle take survey */ },
                onEditClick = { surveyId -> /* Handle edit */ },
                onDeleteClick = { surveyId -> /* Handle delete */ }
            )
        }
    }
}

@Composable
fun SurveysList(
    surveys: List<Survey>,
    onSurveyClick: (String) -> Unit,
    onTakeSurveyClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.padding(vertical = 8.dp)
    ) {
        items(surveys) { survey ->
            SurveyCard(
                survey = survey,
                onSurveyClick = { onSurveyClick(survey.id) },
                onTakeSurveyClick = { onTakeSurveyClick(survey.id) },
                onEditClick = { onEditClick(survey.id) },
                onDeleteClick = { onDeleteClick(survey.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SurveyCard(
    survey: Survey,
    onSurveyClick: () -> Unit,
    onTakeSurveyClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    MaterialTheme.card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSurveyClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Survey icon/icon
                Icon(
                    imageVector = Icons.Default.Poll,
                    contentDescription = "Encuesta",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))

                // Survey info
                Column {
                    Text(
                        text = survey.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${survey.responseCount} respuestas • Finaliza: ${survey.endDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Edit/delete buttons (for admins)
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

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = survey.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                BaseButton(
                    modifier = Modifier.width(120.dp),
                    onClick = { onTakeSurveyClick() },
                    colors = ButtonColorsBase.primary(),
                    label = "Tomar Encuesta"
                )
            }
        }
    }
}

// Sample data
data class Survey(
    val id: String,
    val title: String,
    val description: String,
    val endDate: String,
    val responseCount: Int,
    val isActive: Boolean
)

object SampleData {
    val surveys = listOf(
        Survey(
            id = "1",
            title = "Horario de uso de zonas comunes",
            description = "Propuesta para ajustar los horarios de uso de la piscina y gimnasio durante los meses de verano.",
            endDate = "15 de junio",
            responseCount = 24,
            isActive = true
        ),
        Survey(
            id = "2",
            title = "Selección de nuevo proveedor de limpieza",
            description = "Evaluación de tres propuestas diferentes para el servicio de limpieza de zonas comunes.",
            endDate = "22 de junio",
            responseCount = 18,
            isActive = true
        ),
        Survey(
            id = "3",
            title = "Mejoras en iluminación del parking",
            description = "Propuesta para instalar luces LED con sensor de movimiento en el aparcamiento subterráneo.",
            endDate = "10 de julio",
            responseCount = 31,
            isActive = true
        )
    )
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SurveyScreenPreview() {
    UrbanizaTTheme {
        SurveyScreen()
    }
}