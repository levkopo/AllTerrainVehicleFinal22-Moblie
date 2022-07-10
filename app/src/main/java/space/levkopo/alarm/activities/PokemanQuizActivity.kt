package space.levkopo.alarm.activities

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import space.levkopo.alarm.activities.viewmodels.MainViewModel
import space.levkopo.alarm.models.AlarmModel
import space.levkopo.alarm.ui.components.AlarmItem
import space.levkopo.alarm.ui.components.VKDivider
import space.levkopo.alarm.ui.theme.AlarmTheme
import space.levkopo.alarm.ui.theme.VKSansDisplayFont
import java.util.*

class PokemanQuizActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlarmTheme {
                Surface(color = MaterialTheme.colors.background) {
                    QuizScreen()
                }
            }
        }
    }
}

@Composable
private fun QuizScreen() {
    val text = remember { mutableStateOf("") }
    val screen = remember { mutableStateOf("main") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Pokeman Quiz",
                            fontFamily = VKSansDisplayFont,
                        )
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground,
                    elevation = 0.dp,
                )

                VKDivider()
            }
        },
    ) {
        AnimatedVisibility(screen.value=="main") {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Назовите покемона на английском!")

                TextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                    },
                )

                Button(onClick = {
                    //Не успел :(
                    screen.value = "true"
                }) {
                    Text("Готово")
                }
            }
        }

        AnimatedVisibility(screen.value=="false") {
            Text("Увы, но вы его назвали неверно. Попытайтесь в следующий раз :(")
        }
    }
}
