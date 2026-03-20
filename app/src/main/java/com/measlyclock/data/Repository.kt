package com.measlyclock.data

import androidx.compose.ui.graphics.Color
import com.measlyclock.data.db.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class Repository(
    private val alarmSetDao: AlarmSetDao,
    private val alarmDao: AlarmDao,
    private val cycleGroupDao: CycleGroupDao
) {
    val alarmSetsWithAlarms: Flow<List<AlarmSetWithAlarms>> =
        alarmSetDao.observeAllWithAlarms()

    val cycleGroups: Flow<List<CycleGroupEntity>> = cycleGroupDao.observeAll()

    suspend fun addAlarmSet(name: String, color: Color, type: SetType, groupId: String? = null) {
        val id = UUID.randomUUID().toString()
        alarmSetDao.upsert(
            AlarmSetEntity(id = id, name = name, colorValue = color.value.toLong(), type = type, groupId = groupId)
        )
    }

    suspend fun deleteAlarmSet(id: String) {
        alarmSetDao.deleteById(id)
    }

    suspend fun setStandaloneActive(id: String, active: Boolean) {
        alarmSetDao.setActive(id, active)
    }

    suspend fun setCycleGroupActiveSet(groupId: String, activeSetId: String?) {
        cycleGroupDao.setActiveSet(groupId, activeSetId)
    }

    suspend fun upsertCycleGroup(group: CycleGroupEntity) {
        cycleGroupDao.upsert(group)
    }

    @Volatile private var seeded = false

    /** Seeds sample data into an empty database on first launch. No-op if data already exists. */
    suspend fun seedIfEmpty(alarmSets: List<AlarmSetWithAlarms>) {
        if (seeded || alarmSets.isNotEmpty()) return
        seeded = true
        cycleGroupDao.upsert(
            CycleGroupEntity(
                id = "group_work",
                name = "Work Location",
                memberSetIds = listOf("set_office", "set_home_office"),
                activeSetId = null
            )
        )
        sampleAlarmSets.forEach { set ->
            alarmSetDao.upsert(
                AlarmSetEntity(
                    id = set.id,
                    name = set.name,
                    colorValue = set.color.value.toLong(),
                    type = set.type,
                    groupId = set.groupId,
                    isActive = set.isActive
                )
            )
            set.alarms.forEach { alarm ->
                alarmDao.upsert(
                    AlarmEntity(
                        id = alarm.id,
                        alarmSetId = set.id,
                        label = alarm.label,
                        hour = alarm.hour,
                        minute = alarm.minute,
                        repeatDays = alarm.repeatDays,
                        enabled = alarm.enabled
                    )
                )
            }
        }
    }
}
