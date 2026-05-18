package com.example.urbanizat.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.urbanizat.ui.theme.UrbanizaTTheme
import com.example.urbanizat.utils.ButtonColorsBase
import com.example.urbanizat.utils.ROUNDED_BUTTON_CORNERS

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonColorsBase.primary(),
    label: String,
    enable: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(ROUNDED_BUTTON_CORNERS),
        colors = colors,
        enabled = enable,
        modifier = modifier.fillMaxWidth(0.9f)
    ) {
        Text(
            text = label.uppercase()
        )
    }
}


@Preview(showBackground = false)
@Composable
fun BaseButtonPreview() {
    UrbanizaTTheme {
        BaseButton(
            onClick = {},
            colors = ButtonColorsBase.secondary(),
            label = "Test"
        )
    }
}