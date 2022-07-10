package space.levkopo.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import space.levkopo.alarm.models.AlarmModel

class AlarmService: Service() {
    var ringtone: Ringtone? = null
    private var manager: NotificationManagerCompat? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        manager = NotificationManagerCompat.from(baseContext)
        sendAlarmNotification()

        ringtone = RingtoneManager.getRingtone(applicationContext, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        ringtone?.play()

        val repository = Repository(baseContext)
        repository.getById(intent.getIntExtra(ALARM_ID, 0)) { alarm ->
            alarm.enable = false
            repository.update(alarm)
        }

        return START_STICKY
    }

    override fun onUnbind(intent: Intent): Boolean {
        super.onUnbind(intent)
        return false
    }

    override fun onDestroy() {
        ringtone?.stop()
        stopForeground(true)
    }


    private fun sendAlarmNotification() {
        val notification = NotificationCompat.Builder(baseContext, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(getString(R.string.alarm_title))
            .setContentText(getString(R.string.alarm_description))
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(NotificationCompat.Action(
                null, getString(R.string.stop), PendingIntent.getBroadcast(baseContext, 0,
                    Intent(baseContext, StopAlarmReceiver::class.java), Compat.FLAG_IMMUTABLE)
            ))
            .build()

        manager?.createNotificationChannel(
            NotificationChannelCompat.Builder(NOTIFICATION_CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH)
                .setName(getString(R.string.alarm))
                .build())

        startForeground(NOTIFICATION_ID, notification)
    }

    companion object {
        const val ALARM_ID = "alarmId"

        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL = "alarm"

        fun getAlarmIntent(context: Context, alarm: AlarmModel): PendingIntent {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(ALARM_ID, alarm.id)

            return PendingIntent.getBroadcast(context, 0, intent, Compat.FLAG_IMMUTABLE)
        }
    }
}