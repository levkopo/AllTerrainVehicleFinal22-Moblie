package space.levkopo.alarm.utils

import android.app.PendingIntent
import android.os.Build
import org.json.JSONArray

object Compat {
    val FLAG_IMMUTABLE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT } else { PendingIntent.FLAG_UPDATE_CURRENT }

    fun <T> JSONArray.map(callback: (Any?) -> T): List<T> {
        val arrayList = arrayListOf<T>()
        for(item in 0 until length())
            arrayList.add(callback(get(item)))

        return arrayList
    }
}