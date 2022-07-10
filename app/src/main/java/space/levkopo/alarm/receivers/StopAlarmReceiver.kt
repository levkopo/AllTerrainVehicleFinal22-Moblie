package space.levkopo.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import space.levkopo.alarm.activities.PokemanQuizActivity
import space.levkopo.alarm.services.AlarmService

class StopAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity(Intent(context, PokemanQuizActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        context.stopService(Intent(context, AlarmService::class.java))
    }
}