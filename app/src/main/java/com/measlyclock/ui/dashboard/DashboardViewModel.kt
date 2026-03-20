package com.measlyclock.ui.dashboard

import androidx.lifecycle.ViewModel
import com.measlyclock.data.Alarm
import com.measlyclock.data.AlarmSet
import com.measlyclock.data.CycleGroup
import com.measlyclock.data.SetType
import com.measlyclock.data.sampleAlarmSets
import com.measlyclock.data.sampleCycleGroups
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate

data class DashboardUiState(
    val alarmSets: List<AlarmSet> = emptyList(),
    val cycleGroups: List<CycleGroup> = emptyList()
)

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            alarmSets = sampleAlarmSets,
            cycleGroups = sampleCycleGroups
        )
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun cycleSetState(setId: String) {
        _uiState.update { state ->
            val set = state.alarmSets.find { it.id == setId } ?: return@update state

            when (set.type) {
                SetType.STANDALONE -> {
                    val updatedSets = state.alarmSets.map {
                        if (it.id == setId) it.copy(isActive = !it.isActive) else it
                    }
                    state.copy(alarmSets = updatedSets)
                }
                SetType.GROUPED -> {
                    val groupId = set.groupId ?: return@update state
                    val group = state.cycleGroups.find { it.id == groupId } ?: return@update state
                    val members = group.memberSetIds
                    val currentIndex = members.indexOf(group.activeSetId)
                    // Cycle: each member in order, then null (none), then back to first
                    val nextActiveSetId = when {
                        currentIndex == -1 -> members.first()           // null -> first
                        currentIndex < members.size - 1 -> members[currentIndex + 1]  // next member
                        else -> null                                     // last -> null
                    }
                    val updatedGroups = state.cycleGroups.map {
                        if (it.id == groupId) it.copy(activeSetId = nextActiveSetId) else it
                    }
                    state.copy(cycleGroups = updatedGroups)
                }
            }
        }
    }

    fun getActiveAlarmsForSet(setId: String): List<Alarm> {
        val state = _uiState.value
        val set = state.alarmSets.find { it.id == setId } ?: return emptyList()
        val today: DayOfWeek = LocalDate.now().dayOfWeek

        return when (set.type) {
            SetType.STANDALONE -> {
                if (!set.isActive) emptyList()
                else set.alarms.filter { alarm ->
                    alarm.enabled && (alarm.repeatDays.isEmpty() || today in alarm.repeatDays)
                }
            }
            SetType.GROUPED -> {
                val groupId = set.groupId ?: return emptyList()
                val group = state.cycleGroups.find { it.id == groupId } ?: return emptyList()
                if (group.activeSetId != setId) emptyList()
                else set.alarms.filter { alarm ->
                    alarm.enabled && (alarm.repeatDays.isEmpty() || today in alarm.repeatDays)
                }
            }
        }
    }

    fun getGroupStateLabel(setId: String): String? {
        val state = _uiState.value
        val set = state.alarmSets.find { it.id == setId } ?: return null
        if (set.type != SetType.GROUPED) return null
        val group = state.cycleGroups.find { it.id == set.groupId } ?: return null
        val activeName = state.alarmSets.find { it.id == group.activeSetId }?.name
        return activeName ?: "None"
    }
}
