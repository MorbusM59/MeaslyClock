package com.measlyclock.data.db

import androidx.room.TypeConverter
import com.measlyclock.data.SetType
import java.time.DayOfWeek

class Converters {
    @TypeConverter
    fun setTypeToString(type: SetType): String = type.name

    @TypeConverter
    fun stringToSetType(value: String): SetType = SetType.valueOf(value)

    @TypeConverter
    fun daySetToString(days: Set<DayOfWeek>): String =
        days.joinToString(",") { it.value.toString() }

    @TypeConverter
    fun stringToDaySet(value: String): Set<DayOfWeek> =
        if (value.isEmpty()) emptySet()
        else value.split(",").map { DayOfWeek.of(it.trim().toInt()) }.toSet()

    @TypeConverter
    fun stringListToString(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun stringToStringList(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split(",")
}
