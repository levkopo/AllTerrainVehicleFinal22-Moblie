package space.levkopo.alarm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Chip(selected: Boolean, text: String, modifier: Modifier = Modifier, onSelected: () -> Unit) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.primary.copy(
                alpha = 0.05f
            )
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onPrimary
            else -> MaterialTheme.colors.onSurface
        },
        shape = CircleShape,
        modifier = modifier.clickable { onSelected() },
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            )
        )
    }
}