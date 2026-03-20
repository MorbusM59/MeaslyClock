package com.measlyclock.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.DayOfWeek

@Entity(
    tableName = "alarms",
    foreignKeys = [
        ForeignKey(
            entity = AlarmSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["alarmSetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("alarmSetId")]
)
data class AlarmEntity(
    @PrimaryKey val id: String,
    val alarmSetId: String,
    val label: String,
    val hour: Int,
    val minute: Int,
    val repeatDays: Set<DayOfWeek>,
    val enabled: Boolean = true
)
