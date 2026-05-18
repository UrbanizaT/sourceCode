package com.example.urbanizat.ui.components.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbanizat.R
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun FormHeader(
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    label: String
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier.weight(0.5f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.urbanizate),
                contentDescription = "Logo of UrbanizaT",
                modifier = Modifier
                    .size(175.dp)
                    .aspectRatio(16f / 9f)
                    .fillMaxSize()
            )
        }
        Text(
            text = label,
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun FormHeaderPreview() {
    UrbanizaTTheme {
        FormHeader(
            label = "Crear una cuenta"
        )
    }
}