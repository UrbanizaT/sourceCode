package com.example.urbanizat.ui.components.forms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun InputCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    label: @Composable (() -> Unit)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkmarkColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(0.dp)
        )
        label.invoke()

    }
}

@Preview(showBackground = false)
@Composable
fun InputCheckboxPreview() {

    var isChecked by remember { mutableStateOf(false) }

    UrbanizaTTheme {
        InputCheckbox(
            checked = isChecked,
            onCheckedChange = { isChecked = !isChecked },
            label = {
                Text(
                    buildAnnotatedString {
                        append("Aceptas los ")
                        withLink(
                            LinkAnnotation.Url(
                                url = "https://developer.android.com/",
                                styles = TextLinkStyles(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.secondary,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    focusedStyle = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        textDecoration = TextDecoration.Underline,
                                        background = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                    ),
                                    hoveredStyle = SpanStyle(
                                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    pressedStyle = SpanStyle(
                                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                                        textDecoration = TextDecoration.Underline
                                    )
                                )

                            )
                        ) {
                            append("términos y condiciones")
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
    }
}