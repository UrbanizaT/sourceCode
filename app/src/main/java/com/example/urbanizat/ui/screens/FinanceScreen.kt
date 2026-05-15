package com.example.urbanizat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.progressindicator.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
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
fun FinanceScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top app bar
        androidx.compose.material3.TopAppBar(
            title = { Text("Transparencia Financiera") },
            navigationIcon = {
                androidx.compose.material3.IconButton(onClick = { /* Navigate back */ }) {
                    androidx.compose.material3.Icon(
                        androidx.compose.material3.icons.default.ArrowBack,
                        contentDescription = "Atrás"
                    )
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

            // Summary cards
            SummaryCardsRow()

            Spacer(modifier = Modifier.height(16.dp))

            // Filter
            FinancialFilter()

            Spacer(modifier = Modifier.height(16.dp))

            // Chart placeholder
            FinancialChart()

            Spacer(modifier = Modifier.height(24.dp))

            // Recent transactions
            Text(
                text = "Transacciones recientes",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            TransactionsList()
        }
    }
}

@Composable
fun SummaryCardsRow() {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        SummaryCard(
            title = "Ingresos Mensuales",
            amount = "2,450.00 €",
            icon = androidx.compose.material3.icons.default.AttachMoney,
            color = androidx.compose.material3.MaterialTheme.colorScheme.primary
        )
        SummaryCard(
            title = "Gastos Mensuales",
            amount = "1,820.00 €",
            icon = androidx.compose.material3.icons.default.ReceiptLong,
            color = androidx.compose.material3.MaterialTheme.colorScheme.error
        )
        SummaryCard(
            title = "Balance",
            amount = "+630.00 €",
            icon = androidx.compose.material3.icons.default.AccountBalance,
            color = androidx.compose.material3.MaterialTheme.colorScheme.success
        )
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    icon: androidx.compose.material3.IconVector,
    color: androidx.compose.ui.graphics.Color
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .width(110.dp)
            .height(100.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = amount,
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun FinancialFilter() {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Período:",
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Button(
                onClick = { /* Show filter dialog */ },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mensual (último mes)",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )
                    androidx.compose.material3.Icon(
                        androidx.compose.material3.icons.default.ExpandMore,
                        contentDescription = "Expandir"
                    )
                }
            }
        }
    }
}

@Composable
fun FinancialChart() {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        // Simple chart placeholder
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(
                text = "Gráfico de Ingresos vs Gastos",
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.width(80.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                // Income bars
                androidx.compose.foundation.layout.Column(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(8.dp, 40.dp)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Ingresos",
                        style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                // Expense bars
                androidx.compose.foundation.layout.Column(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(8.dp, 30.dp)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.error)
                    )
                    Text(
                        text = "Gastos",
                        style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionsList() {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        androidx.compose.foundation.layout.LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.padding(vertical = 8.dp)
        ) {
            items(5) { index ->
                TransactionItem(
                    description = when (index) {
                        0 -> "Cuota de comunidad"
                        1 -> "Mantenimiento ascensor"
                        2 -> "Recepción de fondos"
                        3 -> "Pago factura luz"
                        4 -> "Multas por ruido"
                    },
                    amount = when (index) {
                        0, 2 -> "-300.00 €"
                        1 -> "-150.00 €"
                        3 -> "+85.00 €"
                        4 -> "-50.00 €"
                    },
                    isExpense = index != 2 && index != 3
                )
                if (index < 4) {
                    androidx.compose.foundation.layout.Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItem(
    description: String,
    amount: String,
    isExpense: Boolean = true
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
    ) {
        androidx.compose.foundation.layout.Column {
            Text(
                text = description,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "• " + java.time.LocalDate.now().minusDays((0..30).random()).toString(),
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        androidx.compose.foundation.layout.Spacer(
            modifier = Modifier.weight(1f)
        )

        Text(
            text = amount,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color = if (isExpense)
                androidx.compose.material3.MaterialTheme.colorScheme.error
            else
                androidx.compose.material3.MaterialTheme.colorScheme.success
        )
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun FinanceScreenPreview() {
    UrbanizaTTheme {
        FinanceScreen()
    }
}