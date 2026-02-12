package com.example.urbanizat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.R
import com.example.urbanizat.ui.components.forms.InputCustom
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ROUNDED_CORNERS
import com.example.urbanizat.utils.SPACER_HEIGHT
import com.example.urbanizat.utils.SPACE_INPUTS_FORM

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.urbanizate),
                        contentDescription = "Logo of UrbanizaT",
                        modifier = Modifier
                            .size(150.dp)
                            .aspectRatio(16f / 9f)
                            .fillMaxSize()
                    )
                }
                Text(
                    text = "Crear una cuenta",
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(ROUNDED_CORNERS)
                )
                .fillMaxSize()
                .padding(top = SPACER_HEIGHT),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SPACE_INPUTS_FORM)
            )
        {
            InputCustom(
                value = "Manolo", //TODO: add viewModel logic
                onValueChange = {}, //TODO: add viewModel logic
                label = "Nombre y apellidos",
                placeholder = { Text(text = "Ejemplo: Manolo López")},
                isError = false, //TODO: add viewModel logic
                supportingText = null //TODO: add viewModel logic
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun RegisterScreenPreview() {
    UrbanizaTTheme {
        RegisterScreen()
    }
}