package com.jiujiu.task.data.model

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class Converters {

    @TypeConverter
    fun fromStringToLocalDate(value: String?): LocalDate? {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun fromLocalDateToString(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        return LocalTime.parse(value)
    }

    @TypeConverter
    fun fromLocalTimeToString(date: LocalTime?): String? {
        return date.toString()
    }
}