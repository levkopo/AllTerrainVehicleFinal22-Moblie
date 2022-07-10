package space.levkopo.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import space.levkopo.alarm.Repository
import space.levkopo.alarm.services.AlarmService

class DisableAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val repository = Repository(context)
        repository.getById(intent.getIntExtra(AlarmService.ALARM_ID, 0)) {
            it.enable = false
            repository.update(it)
        }
    }
}