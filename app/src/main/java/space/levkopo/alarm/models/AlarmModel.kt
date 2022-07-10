package space.levkopo.alarm.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "alarm")
class AlarmModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var hour: Int = 0,
    var minute: Int = 0,
    var daysOfWeek: ArrayList<Int> = arrayListOf(),
    var enable: Boolean = true,
) {
    val calendar: Calendar get() = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()

        this[Calendar.HOUR_OF_DAY] = hour
        this[Calendar.MINUTE] = minute
        this[Calendar.SECOND] = 0
        this[Calendar.MILLISECOND] = 0

        val sortedWeekDays = daysOfWeek.sortedBy { it }

        if(sortedWeekDays.isEmpty()) {
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }else{
            val nextWeekDay = sortedWeekDays.map { it }
                .firstOrNull {
                    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                    it > today
                }
                ?: sortedWeekDays.first()

            val todayDayOfWeek = this[Calendar.DAY_OF_WEEK]

            if (todayDayOfWeek < nextWeekDay) {
                this[Calendar.DAY_OF_WEEK] = nextWeekDay
            }else{
                val date = this[Calendar.DAY_OF_MONTH]
                val day = this[Calendar.DAY_OF_WEEK]

                val daysToPostpone = if ((nextWeekDay + 7 - day) % 7 == 0) 7 else (nextWeekDay + 7 - day) % 7
                set(Calendar.DAY_OF_MONTH, date + daysToPostpone)
            }
        }
    }
}