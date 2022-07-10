package space.levkopo.alarm

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.levkopo.alarm.models.AlarmDatabase
import space.levkopo.alarm.models.AlarmModel

class Repository(context: Context) {
    private val alarmDao = AlarmDatabase.getInstance(context).alarm()
    private val scope = CoroutineScope(Dispatchers.Main)

    fun getAll() = alarmDao.getAll()
    fun getById(id: Int, onResult: (AlarmModel) -> Unit) {
        scope.launch(Dispatchers.IO) {
            onResult(alarmDao.getById(id))
        }
    }

    fun getById(id: Int) = alarmDao.getLiveDataById(id)

    fun remove(id: Int) {
        scope.launch(Dispatchers.IO) {
            alarmDao.deleteAlarm(id)
        }
    }

    fun update(alarm: AlarmModel) {
        scope.launch(Dispatchers.IO) {
            alarmDao.update(alarm)
        }
    }

    fun insert(alarm: AlarmModel) {
        scope.launch(Dispatchers.IO) {
            alarmDao.insert(alarm)
        }
    }
}