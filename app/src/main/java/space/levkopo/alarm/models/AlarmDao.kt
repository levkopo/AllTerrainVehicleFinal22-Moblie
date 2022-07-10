package space.levkopo.alarm.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: AlarmModel)

    @Update
    fun update(item: AlarmModel)

    @Query("SELECT * FROM alarm")
    fun getAll(): LiveData<List<AlarmModel>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    suspend fun getById(id: Int): AlarmModel

    @Query("SELECT * FROM alarm WHERE id = :id")
    fun getLiveDataById(id: Int): LiveData<AlarmModel>

    @Query("DELETE FROM alarm WHERE id = :id")
    fun deleteAlarm(id: Int)
}