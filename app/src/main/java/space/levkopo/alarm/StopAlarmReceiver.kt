package space.levkopo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.stopService(Intent(context, AlarmService::class.java))
    }
}