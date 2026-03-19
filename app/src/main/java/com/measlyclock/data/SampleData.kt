package com.measlyclock.data

import androidx.compose.ui.graphics.Color
import java.time.DayOfWeek

val sampleCycleGroup = CycleGroup(
    id = "group_work",
    name = "Work Location",
    memberSetIds = listOf("set_office", "set_home_office"),
    activeSetId = null
)

val sampleAlarmSets = listOf(
    AlarmSet(
        id = "set_daily",
        name = "Daily",
        color = Color(0xFF4CAF50),
        type = SetType.STANDALONE,
        isActive = true,
        alarms = listOf(
            Alarm(
                id = "alarm_daily_1",
                label = "Wake up",
                hour = 7,
                minute = 0,
                repeatDays = DayOfWeek.entries.toSet()
            ),
            Alarm(
                id = "alarm_daily_2",
                label = "Morning stretch",
                hour = 7,
                minute = 30,
                repeatDays = DayOfWeek.entries.toSet()
            ),
            Alarm(
                id = "alarm_daily_3",
                label = "Night wind-down",
                hour = 22,
                minute = 0,
                repeatDays = DayOfWeek.entries.toSet()
            )
        )
    ),
    AlarmSet(
        id = "set_medication",
        name = "Medication",
        color = Color(0xFFE91E63),
        type = SetType.STANDALONE,
        isActive = false,
        alarms = listOf(
            Alarm(
                id = "alarm_med_1",
                label = "Morning pill",
                hour = 8,
                minute = 0,
                repeatDays = DayOfWeek.entries.toSet()
            ),
            Alarm(
                id = "alarm_med_2",
                label = "Evening pill",
                hour = 20,
                minute = 0,
                repeatDays = DayOfWeek.entries.toSet()
            )
        )
    ),
    AlarmSet(
        id = "set_office",
        name = "Office",
        color = Color(0xFF2196F3),
        type = SetType.GROUPED,
        groupId = "group_work",
        alarms = listOf(
            Alarm(
                id = "alarm_office_1",
                label = "Commute alarm",
                hour = 6,
                minute = 30,
                repeatDays = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
            ),
            Alarm(
                id = "alarm_office_2",
                label = "Lunch",
                hour = 12,
                minute = 0,
                repeatDays = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
            ),
            Alarm(
                id = "alarm_office_3",
                label = "Leave office",
                hour = 17,
                minute = 30,
                repeatDays = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
            )
        )
    ),
    AlarmSet(
        id = "set_home_office",
        name = "Home Office",
        color = Color(0xFFFF9800),
        type = SetType.GROUPED,
        groupId = "group_work",
        alarms = listOf(
            Alarm(
                id = "alarm_home_1",
                label = "Start work",
                hour = 8,
                minute = 30,
                repeatDays = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
                )
            ),
            Alarm(
                id = "alarm_home_2",
                label = "Lunch break",
                hour = 12,
                minute = 30,
                repeatDays = setOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.FRIDAY
                )
            ),
            Alarm(
                id = "alarm_home_3",
                label = "Weekend project",
                hour = 10,
                minute = 0,
                repeatDays = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
            )
        )
    )
)

val sampleCycleGroups = listOf(sampleCycleGroup)
