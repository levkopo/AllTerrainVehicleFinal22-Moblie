package space.levkopo.alarm.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import space.levkopo.alarm.ui.theme.DividerColor

@Composable
fun VKDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        color = if(isSystemInDarkTheme()) Color(0x0fffffff) else Color(0x0f000000)
    )
}