package com.measlyclock.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_groups")
data class CycleGroupEntity(
    @PrimaryKey val id: String,
    val name: String,
    val memberSetIds: List<String>,
    val activeSetId: String? = null
)
