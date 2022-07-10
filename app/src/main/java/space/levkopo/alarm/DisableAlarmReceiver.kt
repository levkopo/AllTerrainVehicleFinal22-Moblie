package space.levkopo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DisableAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val repository = Repository(context)
        repository.getById(intent.getIntExtra(AlarmService.ALARM_ID, 0)) {
            it.enable = false
            repository.update(it)
        }
    }
}