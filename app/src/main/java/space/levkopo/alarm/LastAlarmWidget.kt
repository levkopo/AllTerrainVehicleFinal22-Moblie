package space.levkopo.alarm

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
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
    val views = RemoteViews(context.packageName, R.layout.last_alarm_widget)
    val repository = Repository(context)
    repository.getAll().observeForever { alarms ->
        val lastAlarm = alarms.sortedBy { it.enable }.sortedBy { it.calendar.timeInMillis }.firstOrNull()
        if(lastAlarm!=null) {
            views.setViewVisibility(R.id.date, View.VISIBLE)
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
            views.setTextViewText(R.id.date, context.getString(R.string.empty_alarms_list))
        }
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}