package com.example.urbanizat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.R
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .border(2.dp, Color.Yellow)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.border(2.dp, Color.Blue)) {
            Row(
                modifier = Modifier.border(2.dp, Color.Red),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.urbanizate),
                        contentDescription = "Logo of UrbanizaT",
                        modifier = Modifier
                            .size(150.dp)
                            .aspectRatio(16f / 9f)
                            .fillMaxSize()
                    )
                }
                Text(text = "Crear una cuenta")
            }
        }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .border(2.dp, Color.Green)
                .fillMaxSize()
        )
        {

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