package com.measlyclock.data.db

import androidx.room.*

@Dao
interface AlarmDao {
    @Upsert
    suspend fun upsert(entity: AlarmEntity)

    @Delete
    suspend fun delete(entity: AlarmEntity)

    @Query("DELETE FROM alarms WHERE alarmSetId = :setId")
    suspend fun deleteForSet(setId: String)
}
