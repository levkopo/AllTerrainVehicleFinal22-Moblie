package space.levkopo.alarm.activities.viewmodels

import android.app.AlarmManager
import android.app.Application
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import space.levkopo.alarm.services.AlarmService
import space.levkopo.alarm.Repository
import space.levkopo.alarm.models.AlarmModel


class MainViewModel(private val application: Application) : ViewModel() {
    private val repository = Repository(application)
    val alarmManager = getSystemService(application, AlarmManager::class.java)!!
    val alarms = repository.getAll()

    fun getById(id: Int) = repository.getById(id)

    fun remove(alarm: AlarmModel) {
        repository.remove(alarm.id)
        alarmManager.cancel(AlarmService.getAlarmIntent(application, alarm))
    }

    fun insert(alarm: AlarmModel) {
        repository.insert(alarm)
        setupAlarm(alarm)
    }

    private fun setupAlarm(alarm: AlarmModel) {
        val pendingIntent = AlarmService.getAlarmIntent(application, alarm)
        val calendar = alarm.calendar

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
            calendar.timeInMillis,
            pendingIntent
        ), pendingIntent)
    }

    fun update(alarm: AlarmModel) {
        repository.update(alarm)

        alarmManager.cancel(AlarmService.getAlarmIntent(application, alarm))
        if(alarm.enable) {
            setupAlarm(alarm)
        }
    }
}