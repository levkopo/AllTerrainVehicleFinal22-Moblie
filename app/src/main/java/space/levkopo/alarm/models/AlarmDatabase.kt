package space.levkopo.alarm.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import space.levkopo.alarm.database.convertors.ListConverter

@TypeConverters(ListConverter::class)
@Database(entities = [(AlarmModel::class)], version = 1)
abstract class AlarmDatabase: RoomDatabase() {
    abstract fun alarm(): AlarmDao

    companion object {
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(context: Context): AlarmDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlarmDatabase::class.java,
                        "alarms"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}