package com.measlyclock.data.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.measlyclock.data.SetType

@Entity(tableName = "alarm_sets")
data class AlarmSetEntity(
    @PrimaryKey val id: String,
    val name: String,
    val color: Color,
    val type: SetType,
    val groupId: String? = null,
    val isActive: Boolean = false,
    val sortOrder: Int = 0
)
