package com.measlyclock

import android.app.Application
import com.measlyclock.data.Repository
import com.measlyclock.data.db.AppDatabase

class MeaslyClockApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy {
        Repository(
            alarmSetDao = database.alarmSetDao(),
            alarmDao = database.alarmDao(),
            cycleGroupDao = database.cycleGroupDao()
        )
    }
}
