package com.measlyclock.data

import androidx.compose.ui.graphics.Color
import java.time.DayOfWeek

enum class SetType { STANDALONE, GROUPED }

data class Alarm(
    val id: String,
    val label: String,
    val hour: Int,
    val minute: Int,
    val repeatDays: Set<DayOfWeek>,  // empty = one-time
    val enabled: Boolean = true
)

data class AlarmSet(
    val id: String,
    val name: String,
    val color: Color,
    val type: SetType,
    val groupId: String? = null,
    val isActive: Boolean = false,  // for standalone
    val alarms: List<Alarm> = emptyList()
)

data class CycleGroup(
    val id: String,
    val name: String,
    val memberSetIds: List<String>,
    val activeSetId: String? = null  // null = "none"
)
