package space.levkopo.alarm.database.convertors

import androidx.room.TypeConverter
import org.json.JSONArray
import space.levkopo.alarm.utils.Compat.map
import kotlin.collections.ArrayList


object ListConverter {
    @TypeConverter fun toIntList(string: String) = ArrayList(JSONArray(string).map { it.toString().toInt() })
    @TypeConverter fun fromIntList(list: ArrayList<Int>) = JSONArray(list).toString()
}