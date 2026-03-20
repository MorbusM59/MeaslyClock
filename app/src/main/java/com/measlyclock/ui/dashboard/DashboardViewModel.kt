package com.measlyclock.ui.dashboard

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.measlyclock.data.*
import com.measlyclock.data.db.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

data class DashboardUiState(
    val alarmSets: List<AlarmSet> = emptyList(),
    val cycleGroups: List<CycleGroup> = emptyList()
)

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.alarmSetsWithAlarms,
                repository.cycleGroups
            ) { setsWithAlarms, groups ->
                // Seed sample data on first launch
                repository.seedIfEmpty(setsWithAlarms)
                DashboardUiState(
                    alarmSets = setsWithAlarms.map { it.toDomain() },
                    cycleGroups = groups.map { it.toDomain() }
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun cycleSetState(setId: String) {
        val state = _uiState.value
        val set = state.alarmSets.find { it.id == setId } ?: return
        viewModelScope.launch {
            when (set.type) {
                SetType.STANDALONE -> {
                    repository.setStandaloneActive(setId, !set.isActive)
                }
                SetType.GROUPED -> {
                    val groupId = set.groupId ?: return@launch
                    val group = state.cycleGroups.find { it.id == groupId } ?: return@launch
                    val members = group.memberSetIds
                    val currentIndex = members.indexOf(group.activeSetId)
                    val nextActiveSetId = when {
                        currentIndex == -1 -> members.first()
                        currentIndex < members.size - 1 -> members[currentIndex + 1]
                        else -> null
                    }
                    repository.setCycleGroupActiveSet(groupId, nextActiveSetId)
                }
            }
        }
    }

    fun addAlarmSet(name: String, color: Color, type: SetType) {
        viewModelScope.launch {
            repository.addAlarmSet(name, color, type)
        }
    }

    fun deleteAlarmSet(setId: String) {
        viewModelScope.launch {
            repository.deleteAlarmSet(setId)
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

    companion object {
        fun factory(repository: Repository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                DashboardViewModel(repository) as T
        }
    }
}

// --- Domain mappers ---
private fun AlarmSetWithAlarms.toDomain(): AlarmSet = AlarmSet(
    id = alarmSet.id,
    name = alarmSet.name,
    color = alarmSet.color,
    type = alarmSet.type,
    groupId = alarmSet.groupId,
    isActive = alarmSet.isActive,
    alarms = alarms.map { it.toDomain() }
)

private fun AlarmEntity.toDomain(): Alarm = Alarm(
    id = id,
    label = label,
    hour = hour,
    minute = minute,
    repeatDays = repeatDays,
    enabled = enabled
)

private fun CycleGroupEntity.toDomain(): CycleGroup = CycleGroup(
    id = id,
    name = name,
    memberSetIds = memberSetIds,
    activeSetId = activeSetId
)
