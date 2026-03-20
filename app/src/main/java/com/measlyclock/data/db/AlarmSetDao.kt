package com.measlyclock.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmSetDao {
    @Transaction
    @Query("SELECT * FROM alarm_sets ORDER BY sortOrder ASC, name ASC")
    fun observeAllWithAlarms(): Flow<List<AlarmSetWithAlarms>>

    @Upsert
    suspend fun upsert(entity: AlarmSetEntity)

    @Delete
    suspend fun delete(entity: AlarmSetEntity)

    @Query("DELETE FROM alarm_sets WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE alarm_sets SET isActive = :active WHERE id = :id")
    suspend fun setActive(id: String, active: Boolean)
}
