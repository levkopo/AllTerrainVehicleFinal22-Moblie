package space.levkopo.alarm

import android.app.TimePickerDialog
import android.content.Intent
import android.media.Ringtone
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import space.levkopo.alarm.models.AlarmModel
import space.levkopo.alarm.ui.components.AlarmItem
import space.levkopo.alarm.ui.components.VKDivider
import space.levkopo.alarm.ui.theme.AlarmTheme
import space.levkopo.alarm.ui.theme.VKSansDisplayFont
import java.util.*


class MainActivity : ComponentActivity() {
    private lateinit var r: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlarmTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel: MainViewModel = viewModel(
                        LocalViewModelStoreOwner.current!!,
                        "MainViewModel",
                        initializer = { MainViewModel(application) }
                    )

                    AlarmListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
private fun AlarmListScreen(viewModel: MainViewModel) {

    // В последний момент понял почему ничего не работает
    // Спасибо Google за хорошую документацию к будильникам
    // :c
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S&&!viewModel.alarmManager.canScheduleExactAlarms()) {
        LocalContext.current.startActivity(
            Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.parse("package:" + LocalContext.current.packageName)
            )
        )
    }

    val alarms = viewModel.alarms.observeAsState(listOf())
    val calendar = Calendar.getInstance()
    val newAlarmDialog = TimePickerDialog(LocalContext.current,
        {_, selectedHour : Int, selectedMinute: Int ->
            val alarm = AlarmModel(hour = selectedHour, minute = selectedMinute)
            viewModel.insert(alarm)
        }, calendar[Calendar.HOUR_OF_DAY]+1, calendar[Calendar.MINUTE], true
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "VK Будильник",
                            fontFamily = VKSansDisplayFont,
                        )
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground,
                    elevation = 0.dp
                )

                VKDivider()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    newAlarmDialog.show()
                },
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Создать"
                    )
                },
                text = { Text(text = "Создать") }
            )
        }
    ) {
        LazyColumn {
            items(alarms.value.sortedBy { it.calendar.timeInMillis }) {
                AlarmItem(viewModel, it)
                VKDivider()
            }
        }
    }
}
