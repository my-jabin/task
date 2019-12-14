package com.jiujiu.task.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Trip(

        var from: String = "",

        var to: String = "",

        var departureDate: LocalDate = LocalDate.now(),

        var departureTime: LocalTime = LocalTime.now(),

        var arrivalDate: LocalDate = LocalDate.now(),

        var arrivalTime: LocalTime = LocalTime.now()
) {
    var inRemote: Boolean = false // whether this object is persistent in the remote system.

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0


    override fun toString(): String {
        return "from $from($departureDate, $departureTime) to $to($arrivalDate, $arrivalTime) inRemote=$inRemote"
    }
}


