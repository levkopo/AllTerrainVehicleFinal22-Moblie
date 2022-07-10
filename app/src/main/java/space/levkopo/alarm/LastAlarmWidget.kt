package space.levkopo.alarm

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import space.levkopo.alarm.receivers.DisableAlarmReceiver
import space.levkopo.alarm.services.AlarmService
import space.levkopo.alarm.utils.Compat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class LastAlarmWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}
    override fun onDisabled(context: Context) {}
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val repository = Repository(context)
    repository.getAll().observeForever { alarms ->
        val views = RemoteViews(context.packageName, R.layout.last_alarm_widget)
        val lastAlarm = alarms.filter { it.enable }.minByOrNull { it.calendar.timeInMillis }
        if(lastAlarm!=null) {
            views.setViewVisibility(R.id.disable, View.VISIBLE)
            views.setViewVisibility(R.id.date, View.VISIBLE)
            views.setViewVisibility(R.id.no_alarms, View.GONE)

            views.setTextViewText(R.id.date, SimpleDateFormat("HH:mm", Locale.getDefault()).format(lastAlarm.calendar.time))

            views.setOnClickPendingIntent(R.id.disable, PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, DisableAlarmReceiver::class.java)
                    .putExtra(AlarmService.ALARM_ID, lastAlarm.id),
                Compat.FLAG_IMMUTABLE
            ))
        }else{
            views.setViewVisibility(R.id.disable, View.GONE)
            views.setViewVisibility(R.id.date, View.GONE)
            views.setViewVisibility(R.id.no_alarms, View.VISIBLE)
        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}