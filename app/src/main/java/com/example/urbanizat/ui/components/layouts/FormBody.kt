package com.example.urbanizat.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ROUNDED_CORNERS
import com.example.urbanizat.utils.SPACE_INPUTS_FORM

@Composable
fun FormBody(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    shape: Shape = RoundedCornerShape(ROUNDED_CORNERS),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(SPACE_INPUTS_FORM),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = shape
            )
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    )
    {
        content()
    }
}

@Preview
@Composable
fun FormBodyPreview() {
    UrbanizaTTheme {
        FormBody{
            Text(text="Este componente crea la estructura del formulario automáticamente.")
        }
    }
}