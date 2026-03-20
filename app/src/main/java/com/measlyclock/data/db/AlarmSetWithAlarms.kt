package com.measlyclock.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmSetWithAlarms(
    @Embedded val alarmSet: AlarmSetEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "alarmSetId"
    )
    val alarms: List<AlarmEntity>
)
