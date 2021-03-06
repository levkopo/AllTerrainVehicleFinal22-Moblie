package space.levkopo.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import space.levkopo.alarm.services.AlarmService

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, AlarmService::class.java)
            .putExtra(AlarmService.ALARM_ID, intent.getIntExtra(AlarmService.ALARM_ID, -1))
        )
    }
}