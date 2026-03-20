package com.measlyclock.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleGroupDao {
    @Query("SELECT * FROM cycle_groups")
    fun observeAll(): Flow<List<CycleGroupEntity>>

    @Upsert
    suspend fun upsert(entity: CycleGroupEntity)

    @Delete
    suspend fun delete(entity: CycleGroupEntity)

    @Query("UPDATE cycle_groups SET activeSetId = :activeSetId WHERE id = :id")
    suspend fun setActiveSet(id: String, activeSetId: String?)
}
