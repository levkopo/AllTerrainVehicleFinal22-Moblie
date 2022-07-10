package space.levkopo.alarm.ui.components

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import space.levkopo.alarm.activities.viewmodels.MainViewModel
import space.levkopo.alarm.models.AlarmModel
import space.levkopo.alarm.ui.theme.VKSansDisplayFont
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AlarmItem(viewModel: MainViewModel, alarm: AlarmModel) {
    val expanded = remember { mutableStateOf(false) }
    val pickerDialog = TimePickerDialog(
        LocalContext.current,
        {_, selectedHour : Int, selectedMinute: Int ->
            viewModel.update(alarm.apply {
                hour = selectedHour
                minute = selectedMinute
                enable = true
            })
        }, alarm.hour, alarm.minute, true
    )

    val updateWeekOfDay: (Int) -> Unit = {
        alarm.daysOfWeek.apply {
            if (contains(it)) remove(it)
            else add(it)
        }

        viewModel.update(alarm)
    }

    Row(Modifier.fillMaxWidth().padding(Dp(5f)).clickable { expanded.value = !expanded.value }) {
        Column {
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(alarm.calendar.time),
                fontFamily = VKSansDisplayFont,
                fontSize = TextUnit(48f, TextUnitType.Sp),
                fontWeight = FontWeight(500),
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp).clickable {
                    expanded.value = true
                    pickerDialog.show()
                }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp, bottom = 10.dp, end = 15.dp)
            ) {
                Text(
                    text = if(alarm.enable) "Установлен" else "Не установлен",
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                )

                Switch(alarm.enable, onCheckedChange = {
                    alarm.enable = it
                    viewModel.update(alarm)
                })
            }

            AnimatedVisibility(expanded.value) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        DayOfWeekChip(alarm, Calendar.MONDAY, "ПН", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.TUESDAY, "ВТ", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.WEDNESDAY, "СР", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.THURSDAY, "ЧТ", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.FRIDAY, "ПТ", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.SATURDAY, "СБ", updateWeekOfDay)
                        DayOfWeekChip(alarm, Calendar.SUNDAY, "ВС", updateWeekOfDay)
                    }

                    TextButton(onClick = { viewModel.remove(alarm) }) {
                        Text("Удалить")
                    }
                }
            }
        }
    }
}

@Composable
fun DayOfWeekChip(alarm: AlarmModel, dayOfWeek: Int, text: String, onChecked: (Int) -> Unit) {
    Chip(alarm.daysOfWeek.contains(dayOfWeek), text) { onChecked(dayOfWeek) }
}